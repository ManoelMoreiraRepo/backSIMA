package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Infraccion;
import com.sima.intranet.Enumarable.Gerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InfraccionRepository extends JpaRepository<Infraccion,Long> {
    Optional<Infraccion> findByNumero(String numero);
    @Query("SELECT i FROM Infraccion i INNER JOIN Movil m ON i.movil = m WHERE m.gerencia =:gerencia ORDER BY m.gerencia")
    List<Infraccion> getInfraccionesPorGerencia(Gerencia gerencia);
}
