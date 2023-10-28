
package com.sima.intranet.Entity;

import jakarta.persistence.*;

import java.util.*;
import lombok.Data;

/**
 *
 * @author mmnmo
 */
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    @Column
    private String nombreUsuario;
    
    @Column
    private String password;
    
    @Column
    private String correoUsuario;


    public Usuario(String username, String encode) {
        this.nombreUsuario = username;
        this.password = encode;
    }

    public Usuario() {

    }


   /* public void agregarRole(Role elRole){
        if (role==null) role= new ArrayList<>();
       
        role.add(elRole);
        
        elRole.setUsuario(this);
    }*/
    
    /*@OneToMany(mappedBy="usuario",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Role> role;*/

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
   
}
