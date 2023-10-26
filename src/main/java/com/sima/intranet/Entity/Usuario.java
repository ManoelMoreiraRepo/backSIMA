
package com.sima.intranet.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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


    public Usuario(String username, String email, String encode) {
        this.nombreUsuario = username;
        this.correoUsuario = email;
        this.password = encode;
    }

    public Usuario() {

    }


   /* public void agregarRole(Role elRole){
        if (role==null) role= new ArrayList<>();
       
        role.add(elRole);
        
        elRole.setUsuario(this);
    }*/
    
    @OneToMany(mappedBy="usuario",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Role> role;
   
}
