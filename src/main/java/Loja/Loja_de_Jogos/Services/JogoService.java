package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.Models.Imagem;
import Loja.Loja_de_Jogos.Models.Plataforma;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import Loja.Loja_de_Jogos.Repositories.CategoriaRepository;
import Loja.Loja_de_Jogos.dtos.JogoCreateDTO;
import Loja.Loja_de_Jogos.dtos.ImagemCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    public Page<Jogo> listarTodos(Pageable pageable) {
        Page<Jogo> page = jogoRepository.findAll(pageable);
        // Força o carregamento das imagens para cada jogo
        page.forEach(jogo -> {
            if (jogo.getImagens() != null) {
                jogo.getImagens().size();
            }
        });
        return page;
    }

    public Optional<Jogo> buscarPorId(Long id) {
        Optional<Jogo> opt = jogoRepository.findById(id);
        opt.ifPresent(jogo -> {
            if (jogo.getImagens() != null) {
                jogo.getImagens().size();
            }
        });
        return opt;
    }

    public Jogo criar(Jogo jogo) {
        if (jogo.getImagens() != null) {
            jogo.getImagens().forEach(img -> img.setJogo(jogo));
        }
        return jogoRepository.save(jogo);
    }

    // Cria um jogo a partir de DTO (mais robusto para requests HTTP)
    public Jogo criarFromDto(JogoCreateDTO dto) {
        Jogo jogo = new Jogo();
        jogo.setNome(dto.nome);
        jogo.setDescricao(dto.descricao);
        jogo.setValor(dto.valor != null ? dto.valor : null);
        try {
            if (dto.dataLancamento != null && !dto.dataLancamento.isEmpty()) {
                jogo.setDataLancamento(java.time.LocalDate.parse(dto.dataLancamento));
            }
        } catch (Exception e) {
            // ignore parse error
        }
        jogo.setDesenvolvedor(dto.desenvolvedor);
        jogo.setDistribuidor(dto.distribuidor);
        jogo.setCategoria(dto.categoria);
        jogo.setSo(dto.so);
        jogo.setArmazenamento(dto.armazenamento);
        jogo.setProcessador(dto.processador);
        jogo.setMemoria(dto.memoria);
        jogo.setPlacaDeVideo(dto.placaDeVideo);

        // plataformas: map strings to enum where possible
        if (dto.plataformas != null) {
            java.util.List<Plataforma> plats = new java.util.ArrayList<>();
            for (String p : dto.plataformas) {
                try {
                    plats.add(Plataforma.valueOf(p));
                } catch (Exception ignored) {}
            }
            jogo.setPlataformas(plats);
        }

        // categorias: find by id if provided
        if (dto.categorias != null && !dto.categorias.isEmpty()) {
            java.util.List<Long> ids = dto.categorias.stream().map(c -> c.id).collect(java.util.stream.Collectors.toList());
            java.util.List<Loja.Loja_de_Jogos.Models.Categoria> cats = categoriaRepository.findAllById(ids);
            jogo.setCategorias(cats);
        }

        // imagens
        if (dto.imagens != null && !dto.imagens.isEmpty()) {
            java.util.List<Imagem> imgs = new java.util.ArrayList<>();
            for (ImagemCreateDTO im : dto.imagens) {
                Imagem img = new Imagem();
                img.setNome(im.nome != null ? im.nome : "");
                img.setUrl(im.url);
                img.setMainImage(im.isMainImage != null && im.isMainImage);
                img.setJogo(jogo);
                imgs.add(img);
            }
            jogo.setImagens(imgs);
        }

        return jogoRepository.save(jogo);
    }

    public Optional<Jogo> atualizar(Long id, Jogo jogoAtualizado) {
        return jogoRepository.findById(id).map(jogoExistente -> {
            // Lógica de atualização
            jogoExistente.setNome(jogoAtualizado.getNome());
            jogoExistente.setValor(jogoAtualizado.getValor());
            jogoExistente.setDescricao(jogoAtualizado.getDescricao());
            jogoExistente.setDataLancamento(jogoAtualizado.getDataLancamento());
            jogoExistente.setDesenvolvedor(jogoAtualizado.getDesenvolvedor());
            jogoExistente.setDistribuidor(jogoAtualizado.getDistribuidor());
            jogoExistente.setSo(jogoAtualizado.getSo());
            jogoExistente.setArmazenamento(jogoAtualizado.getArmazenamento());
            jogoExistente.setProcessador(jogoAtualizado.getProcessador());
            jogoExistente.setMemoria(jogoAtualizado.getMemoria());
            jogoExistente.setPlacaDeVideo(jogoAtualizado.getPlacaDeVideo());
            jogoExistente.setPlataformas(jogoAtualizado.getPlataformas());
            jogoExistente.setCategorias(jogoAtualizado.getCategorias());
            if (jogoAtualizado.getImagens() != null) {
                jogoAtualizado.getImagens().forEach(img -> img.setJogo(jogoExistente));
                jogoExistente.setImagens(jogoAtualizado.getImagens());
            }
            return jogoRepository.save(jogoExistente);
        });
    }
    public void deletar(Long id) {
        jogoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return jogoRepository.existsById(id);
    }
}
