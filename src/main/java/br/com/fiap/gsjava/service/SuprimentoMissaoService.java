package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.SuprimentoMissaoRequestDTO;
import br.com.fiap.gsjava.model.Missao;
import br.com.fiap.gsjava.model.Suprimento;
import br.com.fiap.gsjava.model.SuprimentoMissao;
import br.com.fiap.gsjava.repository.SuprimentoMissaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SuprimentoMissaoService {

    private final SuprimentoMissaoRepository repository;
    private final SuprimentoService suprimentoService;

    public SuprimentoMissaoService(SuprimentoMissaoRepository repository, SuprimentoService suprimentoService) {
        this.repository = repository;
        this.suprimentoService = suprimentoService;
    }

    @Transactional
    public void cadastrarSuprimentosMissao(Missao missao, Set<SuprimentoMissaoRequestDTO> dto) {
        mapSuprimentosFromDTO(missao, dto);
    }

    @Transactional
    public void atualizarSuprimentosMissao(Missao missao, Set<SuprimentoMissaoRequestDTO> suprimentos) {
        mapSuprimentosFromDTO(missao, suprimentos);
    }

    private void mapSuprimentosFromDTO(Missao missao, Set<SuprimentoMissaoRequestDTO> suprimentos) {
        repository.deleteAll(missao.getSuprimentos());
        missao.setPesoTotal(0d);
        List<SuprimentoMissao> suprimentosMissao = suprimentos.stream().map(suprimentoDto -> {
            Suprimento suprimento = suprimentoService.buscarPorId(suprimentoDto.suprimentoId());
            missao.setPesoTotal(missao.getPesoTotal() + suprimentoDto.quantidade() * suprimento.getPesoKg());
            return new SuprimentoMissao(
                    suprimento,
                    missao,
                    suprimentoDto.quantidade()
            );
        }).toList();
        repository.saveAll(suprimentosMissao);
    }
}
