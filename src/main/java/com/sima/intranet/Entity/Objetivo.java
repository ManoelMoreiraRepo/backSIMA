 
package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Objetivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObjetivo;
    
    @Column
    private String NombreObjetivo;
    
    @Column
    private String DireccionObjetivo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idCliente")
    @JsonIgnoreProperties({"cliente","objetivo","hibernateLazyInitializer","handler"})
    
    private Cliente cliente;

}
