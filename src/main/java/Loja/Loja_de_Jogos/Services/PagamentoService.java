package Loja.Loja_de_Jogos.Services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.time.Instant;

@Service
public class PagamentoService {

    @Value("${pagamento.api.url}")
    private String pagamentoApiUrl;
    @Value("${pagamento.merchant.key}")
    private String merchantKey;
    @Value("${pagamento.merchant.secret}")
    private String merchantSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String calcularAssinatura(String method, String path, String timestamp, String rawBody, String secret) {
        try {
            String signatureBase = method + "\n" + path + "\n" + timestamp + "\n" + rawBody;
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(signatureBase.getBytes(StandardCharsets.UTF_8));
            // Converter bytes para hexadecimal minúsculo
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular assinatura HMAC", e);
        }
    }

    // Remove campo status do response (recursivo)
    private Map<String, Object> removeStatus(Map<String, Object> map) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if ("status".equals(entry.getKey())) continue;
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = removeStatus((Map<String, Object>) value);
            }
            result.put(entry.getKey(), value);
        }
        return result;
    }

    public Map<String, Object> criarIntentPagamento(Map<String, Object> payload) {
        try {
            String path = "/api/merchant/v1/payment-intents";
            String url = pagamentoApiUrl + path;
            Map<String, Object> intentPayload = new HashMap<>();
            intentPayload.put("orderId", String.valueOf(payload.getOrDefault("orderId", "00001")));
            intentPayload.put("amount", payload.getOrDefault("amount", 18));
            intentPayload.put("currency", payload.getOrDefault("currency", "BRL"));
            intentPayload.put("paymentMethod", "credit_card");
            Map<String, Object> customer = new HashMap<>();
            Object name = payload.get("cardHolderName");
            if (name == null) name = payload.get("name");
            customer.put("name", name != null ? name : "Usuario Teste");
            Object email = payload.get("email");
            customer.put("email", email != null ? email : "usuario@teste.com");
            intentPayload.put("customer", customer);
            intentPayload.put("callbackUrl", payload.getOrDefault("callbackUrl", "http://localhost:3001/webhooks/trustpay"));
            intentPayload.put("returnUrl", payload.getOrDefault("returnUrl", "http://localhost:3001/checkout/success"));
            Map<String, Object> installments = new HashMap<>();
            installments.put("quantity", 1);
            intentPayload.put("installments", installments);
            String rawBody = objectMapper.writeValueAsString(intentPayload);
            System.out.println("[TRUSTPAY-DEBUG] Payload enviado ao proxy TrustPay:");
            System.out.println(rawBody);
            String method = "POST";
            String timestamp = String.valueOf(Instant.now().getEpochSecond());
            String signature = calcularAssinatura(method, path, timestamp, rawBody, merchantSecret);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", merchantKey);
            headers.set("x-timestamp", timestamp);
            headers.set("x-signature", signature);
            HttpEntity<String> request = new HttpEntity<>(rawBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            Map<String, Object> respBody = response.getBody();
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                if (respBody != null && Boolean.TRUE.equals(respBody.get("success"))) {
                    Map<String, Object> data = (Map<String, Object>) respBody.get("data");
                    if (data != null && data.containsKey("id")) {
                        String id = data.get("id").toString();
                        System.out.println("[TRUSTPAY-DEBUG] Payment Intent criado com id: " + id);
                        return data;
                    }
                }
            }
            System.out.println("[TRUSTPAY-DEBUG] Resposta inesperada do TrustPay: " + respBody);
            return respBody;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar intent de pagamento", e);
        }
    }

    public Map<String, Object> consultarStatus(String intentId) {
        try {
            String method = "GET";
            String path = "/api/merchant/v1/payments/" + intentId + "/status";
            String url = pagamentoApiUrl + path;
            String timestamp = String.valueOf(Instant.now().getEpochSecond());
            String rawBody = ""; // GET não tem body
            String signature = calcularAssinatura(method, path, timestamp, rawBody, merchantSecret);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", merchantKey);
            headers.set("x-timestamp", timestamp);
            headers.set("x-signature", signature);

            HttpEntity<String> request = new HttpEntity<>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
            return removeStatus(responseMap);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar status do pagamento", e);
        }
    }


    public Map<String, Object> capturarPagamento(String intentId, Map<String, Object> payload) {
        System.out.println("CAPTURANDO PAGAMENTO");
        System.out.println(intentId);
        try {
            String method = "POST";
            String path = "/api/merchant/v1/payments/" + intentId + "/capture";
            String url = pagamentoApiUrl + path;
            System.out.println(url);
            String timestamp = String.valueOf(Instant.now().getEpochSecond());

            // Montar payload de captura exatamente como o modelo fornecido
            Map<String, Object> capturePayload = new HashMap<>();
            capturePayload.put("cardNumber", payload.get("cardNumber"));
            capturePayload.put("cardHolderName", payload.get("cardHolderName"));
            capturePayload.put("expirationMonth", payload.get("expirationMonth"));
            capturePayload.put("expirationYear", payload.get("expirationYear"));
            capturePayload.put("cvv", payload.get("cvv"));

            String rawBody = objectMapper.writeValueAsString(capturePayload);
            // Log detalhado do payload enviado ao proxy TrustPay (captura)
            System.out.println("[TRUSTPAY-DEBUG] Payload de captura enviado ao proxy TrustPay:");
            System.out.println(rawBody);

            String signature = calcularAssinatura(method, path, timestamp, rawBody, merchantSecret);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", merchantKey);
            headers.set("x-timestamp", timestamp);
            headers.set("x-signature", signature);

            // Log detalhado dos headers
            System.out.println("[TRUSTPAY-DEBUG] Headers de captura:");
            System.out.println("x-api-key: " + merchantKey);
            System.out.println("x-timestamp: " + timestamp);
            System.out.println("x-signature: " + signature);

            HttpEntity<String> request = new HttpEntity<>(rawBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            // Log detalhado da resposta do proxy TrustPay (captura)
            System.out.println("[TRUSTPAY-DEBUG] Resposta do proxy TrustPay (captura):");
            System.out.println(response.getBody());
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao capturar pagamento", e);
        }
    }

}
