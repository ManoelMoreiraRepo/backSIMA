package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.*;
import lombok.Data;

@Entity
@Data
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    
    @Column
    private String NombreCliente;
    
    @Column
    private String DireccionCliente;
    
    /*public void agregarObjetivo(Objetivo elObjetivo){
    
    if(objetivo == null)  objetivo = new ArrayList<>();
    elObjetivo.setCliente(this);
    }*/
    
    @OneToMany(mappedBy="cliente",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    ,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"cliente","hibernateLazyInitializer","handler"})
    private List<Objetivo> objetivo;
    
    
}
