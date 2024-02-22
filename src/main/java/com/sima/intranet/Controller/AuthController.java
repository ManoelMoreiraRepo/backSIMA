package com.sima.intranet.Controller;

import com.sima.intranet.Dto.LoginRequest;
import com.sima.intranet.Dto.MessageResponse;
import com.sima.intranet.Dto.NuevaCuentaDTO;
import com.sima.intranet.Dto.SignupRequest;
import com.sima.intranet.Entity.Role;
import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Enumarable.ERole;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Repository.RoleRepository;
import com.sima.intranet.Repository.UsuarioRepository;
import com.sima.intranet.Seguridad.UserDetailsImpl;
import com.sima.intranet.Seguridad.jwt.JwtUtils;
import com.sima.intranet.Service.AuthService;
import com.sima.intranet.Service.EmailService;
import com.sima.intranet.Service.UsuarioServiceImpl;
import com.sima.intranet.Util.Strings;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${masterKey}")
    private String MASTER_KEY;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioServiceImpl usuarioService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthService authService;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails , roles.get(0));
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
                /*.body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));*/
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest ,  HttpServletRequest request) {
        if(Strings.isNullOrEmpty(signUpRequest.getMasterKey()) || !signUpRequest.getMasterKey().equals(MASTER_KEY)){
            if(jwtUtils.getJwtFromCookies(request) == null && !signUpRequest.getMasterKey().equals(MASTER_KEY)){
                return ResponseEntity.badRequest().body(new MessageResponse("Usuario no autorizado."));
            }
            Optional<Usuario> usuario = usuarioService.getUsuarioActivo(request);
            Optional<Role> roleModerator = Optional.empty();
            if (usuario.isEmpty()) {
                roleModerator = usuario.get().getRoles().stream().filter(e -> e.getName().equals(ERole.ROLE_MODERATOR)).findFirst();
            }
            if(!roleModerator.isPresent()){
                return ResponseEntity.badRequest().body(new MessageResponse("Usuario no autorizado."));
            }
        }

        try {
            authService.registrarOActualizarUsuario(signUpRequest);
        }catch(ParametroInvalidoException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente."));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }


    @GetMapping("/role")
    public Map getRoleFromToken(HttpServletRequest request) {
        ERole role =  jwtUtils.getRoleFromJwtToken(jwtUtils.getJwtFromCookies(request));
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        if(role == null || username == null){
            return Map.of("role" , null);
        }
        return Map.of("role" , role.name());
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<MessageResponse> enviarMailNuevaCuenta(@RequestBody @Valid NuevaCuentaDTO cuenta){
        boolean envioOk = false;
        try {
            envioOk = emailService.sendEmailNuevaCuenta(cuenta);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(envioOk){
            return ResponseEntity.ok().body(new MessageResponse("Se envio correctamente su solicitud. Pronto le enviaremos un email."));
        }else{
            return  ResponseEntity.badRequest().body(new MessageResponse("No se pudo procesar la solicitud."));
        }
    }
}
