package com.sima.intranet.Entity;

import com.sima.intranet.Enumarable.Gerencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    @ManyToOne
    private Movil movil;

    private String producto;

    private BigDecimal litros;

    private BigDecimal importe;

    private Gerencia gerencia;
}
