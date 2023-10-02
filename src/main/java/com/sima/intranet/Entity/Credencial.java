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
import java.util.Date;
import lombok.Data;


@Entity
@Data
public class Credencial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredencial;
    
    @Column
    private String NombreCredencial;
    
    @Column
    private String CodigoCredencial;
    
    @Column
    private  boolean EstadoCredencial;
    
    @Column
    private Date FechaOtorgamentoCredencial;
    
    @Column
    private Date FechaVencimentoCredencial;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idEmpleado")
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private Empleado empleado;
    
            
}
