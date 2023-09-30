package com.sima.intranet.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;
    
    @Column(name="NombreEmpresa")
    private String NombreEmpresa;
    
    @Column(name="DireccionEmpresa")
    private String DireccionEmpresa;
    
    @Column(name = "CifEmpresa")
    private String CIFEmpresa;
    
    @Column(name = "GrupoEmpresa")
    private int GrupoEmpresa;
    
   
    
}
