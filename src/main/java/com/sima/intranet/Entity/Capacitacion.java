package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sima.intranet.Enumarable.CursoHabilitante;
import jakarta.persistence.*;

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
    private String codigoCapacitacion;
    
    @Column
    private String nombreCapacitacion;
    
    @Column
    private boolean estadoCapacitacion;
    
    @Column
    private LocalDate fechaOtorgamentoCapacitacion;
    
    @Column
    private LocalDate fechaVencimentoCapacitacion;

    @Enumerated(EnumType.STRING)
    private CursoHabilitante tipoCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idEmpleado")
    @JsonIgnoreProperties({"empleado","capacitacion","indumentaria","hibernateLazyInitializer","handler"})
    private Empleado empleado;
}
