package br.com.fiap.gsjava.repository;

import br.com.fiap.gsjava.model.Suprimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuprimentoRepository extends JpaRepository<Suprimento, Long> {
    Page<Suprimento> findAll(Pageable pageable);
}
