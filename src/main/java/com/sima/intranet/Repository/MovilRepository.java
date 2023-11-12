package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Movil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovilRepository extends JpaRepository<Movil, Long> {

    Optional<Movil> findByDominio(String dominio);
}
