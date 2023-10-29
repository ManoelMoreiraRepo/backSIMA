package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

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
