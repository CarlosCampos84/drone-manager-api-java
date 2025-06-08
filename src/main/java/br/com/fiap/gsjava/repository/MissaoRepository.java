package br.com.fiap.gsjava.repository;

import br.com.fiap.gsjava.model.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissaoRepository extends JpaRepository<Missao, Long>, JpaSpecificationExecutor<Missao> {
}
