package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Operacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacion;

    @Column
    private LocalDate fechaInicioOperacion;

    @Column
    private LocalDate fechaFinalOperacion;

    @Column
    private String grillaOperacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpleado")
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private Empleado empleado;

}
