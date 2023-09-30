
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
public class Indumentaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIndumentaria;
    
    /*@Column
    private String CodigoIndumentaria
    */        
    @Column
    private String tipoIndumentaria;
    
    @Column
    private String nombreIndumentaria;
    
    @Column
    private String talleIndumentaria;
    
    @Column
    private Date fechaUltimaEntregaIndumentaria;
     
    @Column
    private Date fechaProximaEntregaIndumentaria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idEmpleado")
    @JsonIgnoreProperties({"empleado","indumentaria","hibernateLazyInitializer","handler"})
    private Empleado empleado;
    


}
