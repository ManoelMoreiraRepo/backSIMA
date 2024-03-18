package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Consumo;
import com.sima.intranet.Entity.Movil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConsumoRepository extends JpaRepository<Consumo,Long> {

    Optional<Consumo> findByMovilAndFecha(Movil movil , LocalDateTime fecha);

    @Query("SELECT c.gerencia , SUM(c.importe) , SUM(c.litros) FROM Consumo c  WHERE c.gerencia IS NOT NULL group by c.gerencia")
    Object[] getTotalesPorGerencia();
    @Query("SELECT c.movil.dominio , c.movil.gerencia , SUM(c.importe) , SUM(c.litros) FROM Consumo c group by c.movil")
    Object[] getTotalPorDominio();

}
