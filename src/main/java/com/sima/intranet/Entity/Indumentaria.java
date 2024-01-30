
package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sima.intranet.Enumarable.ConjuntoIndumentaria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Indumentaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String codigo;
    
    @Column
    private String nombreIndumentaria;

    @Column
    private String familia;

    @Column
    private String talle;

    @Column
    private Long cantidad;

    @Column
    private String modeloIndumentaria;
    
    @Column
    private LocalDate fechaUltimaEntregaIndumentaria;
     
    @Column
    private LocalDate fechaProximaEntregaIndumentaria;

    private ConjuntoIndumentaria conjunto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idEmpleado")
    @JsonIgnoreProperties({"empleado","indumentaria","hibernateLazyInitializer","handler"})
    private Empleado empleado;
    


}
