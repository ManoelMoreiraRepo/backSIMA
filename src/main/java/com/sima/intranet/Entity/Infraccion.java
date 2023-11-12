package com.sima.intranet.Entity;

import com.sima.intranet.Enumarable.EstadoInfraccion;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Infraccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaActa;
    @ManyToOne
    private Movil movil;

    private String numero;

    private BigDecimal importe;

    private String motivo;

    private String asignado;
    @ManyToOne
    private Empleado empleado;

    private String formaPago;

    private EstadoInfraccion estado;


}
