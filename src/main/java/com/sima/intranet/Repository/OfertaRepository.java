package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Oferta;
import com.sima.intranet.Enumarable.Gerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OfertaRepository extends JpaRepository <Oferta,Long> {

    Optional<Oferta> findByCodigoAndGerencia(String codigo , Gerencia gerencia);
}
