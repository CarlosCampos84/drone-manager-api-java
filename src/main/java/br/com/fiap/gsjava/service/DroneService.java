package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.DroneRequestDTO;
import br.com.fiap.gsjava.dto.DroneResponseDTO;
import br.com.fiap.gsjava.model.Drone;
import br.com.fiap.gsjava.model.DroneFilter;
import br.com.fiap.gsjava.repository.DroneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

    private final DroneRepository repository;

    public DroneService(DroneRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(DroneRequestDTO dto) {
        System.out.println(dto);
        Drone drone = new Drone();
        drone.setNome(dto.nome());
        drone.setTipo(dto.tipo());
        drone.setCapacidadeKg(dto.capacidadeKg());
        System.out.println(drone.getCapacidadeKg());
        System.out.println(drone.getTipo());
        repository.save(drone);
    }

    public Page<DroneResponseDTO> findAll(DroneFilter filter, Pageable pageable) {
        Specification<Drone> spec = (root, query, cb) -> cb.conjunction();

        if (filter.tipo() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("tipo")), "%" + filter.tipo().toLowerCase() + "%"));
        }
        if (filter.capacidadeKgMin() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("capacidadeKg"), filter.capacidadeKgMin()));
        }
        if (filter.capacidadeKgMax() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("capacidadeKg"), filter.capacidadeKgMax()));
        }

        return repository.findAll(spec, pageable).map(DroneResponseDTO::fromDrone);
    }

    public Drone buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Drone não encontrado"));
    }

    public DroneResponseDTO atualizar(Long id, DroneRequestDTO dto) {
        Drone existingDrone = buscarPorId(id);
        existingDrone.setNome(dto.nome());
        existingDrone.setTipo(dto.tipo());
        existingDrone.setCapacidadeKg(dto.capacidadeKg());
        return DroneResponseDTO.fromDrone(repository.save(existingDrone));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}