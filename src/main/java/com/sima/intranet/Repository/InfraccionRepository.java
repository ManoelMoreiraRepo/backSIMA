package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Infraccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfraccionRepository extends JpaRepository<Infraccion,Long> {
    Optional<Infraccion> findByNumero(String numero);
}
