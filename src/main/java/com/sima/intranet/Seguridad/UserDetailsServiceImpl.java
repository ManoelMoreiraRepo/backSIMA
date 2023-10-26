package com.sima.intranet.Seguridad;

import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return (UserDetails) UserDetailsImpl.build(user);
    }
}
