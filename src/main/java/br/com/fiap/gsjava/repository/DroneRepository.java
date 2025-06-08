package br.com.fiap.gsjava.repository;

import br.com.fiap.gsjava.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DroneRepository extends JpaRepository<Drone, Long>, JpaSpecificationExecutor<Drone> {
}
