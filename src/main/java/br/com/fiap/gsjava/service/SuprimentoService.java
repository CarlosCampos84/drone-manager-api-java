package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.SuprimentoRequestDTO;
import br.com.fiap.gsjava.dto.SuprimentoResponseDTO;
import br.com.fiap.gsjava.model.Suprimento;
import br.com.fiap.gsjava.repository.SuprimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SuprimentoService {

    private final SuprimentoRepository repository;

    public SuprimentoService(SuprimentoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(SuprimentoRequestDTO dto) {
        Suprimento suprimento = new Suprimento();
        suprimento.setNome(dto.nome());
        suprimento.setDescricao(dto.descricao());
        suprimento.setPesoKg(dto.pesoKg());
        repository.save(suprimento);
    }

    public Page<SuprimentoResponseDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(SuprimentoResponseDTO::fromSuprimento);
    }

    public Suprimento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suprimento não encontrado"));
    }

    public SuprimentoResponseDTO atualizar(Long id, SuprimentoRequestDTO suprimento) {
        Suprimento existingSuprimento = buscarPorId(id);
        existingSuprimento.setNome(suprimento.nome());
        existingSuprimento.setDescricao(suprimento.descricao());
        existingSuprimento.setPesoKg(suprimento.pesoKg());
        return SuprimentoResponseDTO.fromSuprimento(repository.save(existingSuprimento));
    }

    public void deletar(Long id) {
        Suprimento suprimento = buscarPorId(id);
        repository.delete(suprimento);
    }
}
