package com.sima.intranet.Entity;

import com.sima.intranet.Enumarable.EstadoMovil;
import com.sima.intranet.Enumarable.Gerencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @Column( unique=true )
    private String dominio;

    private EstadoMovil estado;

    private String asignado;

    private String destino;

    private Gerencia gerencia;
}
