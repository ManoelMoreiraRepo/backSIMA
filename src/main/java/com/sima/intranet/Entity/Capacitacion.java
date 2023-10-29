package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Entity
@Data
public class Capacitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCapacitacion;
    
    @Column
    private String CodigoCapacitacion;
    
    @Column
    private String nombreCapacitacion;
    
    @Column
    private  boolean EstadoCapacitacion;
    
    @Column
    private LocalDate FechaOtorgamentoCapacitacion;
    
    @Column
    private LocalDate FechaVencimentoCapacitacion;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idEmpleado")
    @JsonIgnoreProperties({"empleado","capacitacion","indumentaria","hibernateLazyInitializer","handler"})
    private Empleado empleado;
}
