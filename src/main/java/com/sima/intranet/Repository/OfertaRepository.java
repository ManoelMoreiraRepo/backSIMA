package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Oferta;
import com.sima.intranet.Enumarable.Gerencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfertaRepository extends JpaRepository <Oferta,Long> {

    Optional<Oferta> findByCodigoAndGerencia(String codigo , Gerencia gerencia);
}
