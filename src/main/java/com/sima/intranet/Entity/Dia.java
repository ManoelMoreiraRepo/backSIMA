package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sima.intranet.Enumarable.EstadoDia;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private Double horas;

    private EstadoDia estado;
    @ManyToOne
    @JsonIgnore
    private Empleado empleado;
}
