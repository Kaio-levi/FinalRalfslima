package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Cargo;
import Loja.Loja_de_Jogos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    public Optional<Cargo> buscarPorId(Long id) {
        return cargoRepository.findById(id);
    }

    public Cargo salvar(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo atualizar(Long id, Cargo cargoAtualizado) {
        return cargoRepository.findById(id).map(cargo -> {
            cargo.setNome(cargoAtualizado.getNome());
            return cargoRepository.save(cargo);
        }).orElse(null);
    }

    public boolean deletar(Long id) {
        if (cargoRepository.existsById(id)) {
            cargoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}