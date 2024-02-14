package com.sima.intranet.Service;

import com.sima.intranet.Dto.SignupRequest;
import com.sima.intranet.Entity.Role;
import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Enumarable.ERole;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Repository.RoleRepository;
import com.sima.intranet.Repository.UsuarioRepository;
import com.sima.intranet.Util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public Usuario registrarOActualizarUsuario(SignupRequest signUpRequest) throws ParametroInvalidoException{
        if(Strings.isNullOrEmpty(signUpRequest.getPassword()) || Strings.isNullOrEmpty(signUpRequest.getUsername())){
            throw new ParametroInvalidoException("Error: No puede enviar usuario o contraseña vacia.");
        }

        if(signUpRequest.getId() == 0L){
            if (userRepository.existsByNombreUsuario(signUpRequest.getUsername())) {
                throw new ParametroInvalidoException("Error: Nombre de usuario no disponible.");
            }
        }else{
            Optional<Usuario> usuarioByID = userRepository.findById(signUpRequest.getId());
            if (userRepository.existsByNombreUsuario(signUpRequest.getUsername()) && usuarioByID.isPresent() &&!usuarioByID.get().getNombreUsuario().equals(signUpRequest.getUsername())) {
                throw new ParametroInvalidoException("Error: Nombre de usuario no disponible.");
            }
        }
        Usuario user = userRepository.findById(signUpRequest.getId()).orElse(new Usuario());
        if(Strings.isNotNullOrEmpty(signUpRequest.getEmail())){
            user.setCorreoUsuario(signUpRequest.getEmail());
        }
        if(Strings.isNotNullOrEmpty(signUpRequest.getUsername())){
            user.setNombreUsuario(signUpRequest.getUsername());
        }
        if(Strings.isNotNullOrEmpty(signUpRequest.getPassword())){
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
        }

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role no encontrado"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role no encontrado"));
                        roles.add(adminRole);

                        break;
                    case "ROLE_MODERATOR":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role no encontrado"));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role no encontrado"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
