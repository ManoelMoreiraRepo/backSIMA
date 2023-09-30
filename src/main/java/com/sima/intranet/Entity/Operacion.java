package com.sima.intranet.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Data;

@Entity
@Data
public class Operacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacion;

    @Column
    private Date fecahaInicioOperacion;

    @Column
    private Date FechaFinalOperacion;

    @Column
    private String grillaOperacion;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Empleado empleado;

}
