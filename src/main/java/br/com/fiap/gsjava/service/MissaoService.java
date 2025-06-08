package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.MissaoRequestDTO;
import br.com.fiap.gsjava.dto.MissaoResponseDTO;
import br.com.fiap.gsjava.model.Drone;
import br.com.fiap.gsjava.model.Missao;
import br.com.fiap.gsjava.model.MissaoFilter;
import br.com.fiap.gsjava.model.enums.StatusMissao;
import br.com.fiap.gsjava.repository.MissaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MissaoService {

    private final MissaoRepository repository;
    private final DroneService droneService;

    public MissaoService(MissaoRepository repository, DroneService droneService) {
        this.repository = repository;
        this.droneService = droneService;
    }

    @Transactional
    public Missao cadastrar(MissaoRequestDTO dto) {
        Drone drone = droneService.buscarPorId(dto.droneId());
        if (drone.getMissoes().stream().anyMatch(m -> m.getStatus() == StatusMissao.EM_ANDAMENTO)) {
            throw new ValidationException("Drone já está em uma missão ativa");
        }
        Missao missao = new Missao();
        missao.setDescricao(dto.descricao());
        missao.setDataInicio(LocalDateTime.now());
        missao.setStatus(StatusMissao.EM_ANDAMENTO);
        missao.setDrone(drone);
        missao.setPesoTotal(0d);
        return repository.save(missao);
    }

    public Page<MissaoResponseDTO> listarTodos(MissaoFilter filter, Pageable pageable) {
        Specification<Missao> spec = (root, query, cb) -> cb.conjunction();

        if (filter.status() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("status")), "%" + filter.status().toLowerCase() + "%"));
        }
        if (filter.droneId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("drone").get("id"), filter.droneId()));
        }
        return repository.findAll(spec, pageable).map(MissaoResponseDTO::fromMissao);
    }

    public Missao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Missão não encontrada"));
    }

    @Transactional
    public Missao atualizar(Long id, MissaoRequestDTO dto) {
        Missao missao = buscarPorId(id);
        if (missao.getStatus() == StatusMissao.CANCELADO) {
            throw new IllegalArgumentException("Não é possível atualizar uma missão cancelada");
        }
        if (missao.getStatus() == StatusMissao.CONCLUIDO) {
            throw new IllegalArgumentException("Não é possível atualizar uma missão concluída");
        }
        Drone drone = droneService.buscarPorId(dto.droneId());
        missao.setDescricao(dto.descricao());
        missao.setDrone(drone);
        return repository.save(missao);
    }

    public void deletar(Long id) {
        Missao missao = buscarPorId(id);
        if (missao.getStatus() == StatusMissao.CANCELADO) {
            throw new IllegalArgumentException("Essa missão já foi cancelada");
        }
        if (missao.getStatus() == StatusMissao.CONCLUIDO) {
            throw new IllegalArgumentException("Essa missão já foi concluída");
        }
        missao.setStatus(StatusMissao.CANCELADO);
        missao.setDataFim(LocalDateTime.now());
        repository.save(missao);
    }

    public void concluir(Long id) {
        Missao missao = buscarPorId(id);
        if (missao.getStatus() == StatusMissao.CANCELADO) {
            throw new IllegalArgumentException("Essa missão já foi cancelada");
        }
        missao.setStatus(StatusMissao.CONCLUIDO);
        missao.setDataFim(LocalDateTime.now());
        repository.save(missao);
    }
}
