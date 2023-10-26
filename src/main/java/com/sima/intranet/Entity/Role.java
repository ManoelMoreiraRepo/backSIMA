package com.sima.intranet.Entity;

import com.sima.intranet.Enumarable.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;
    
    @Column
    private String NombreRole;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    
}
