package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByNombreUsuario(String username);

    Boolean existsByNombreUsuario(String username);

    Boolean existsByCorreoUsuario(String email);
}
