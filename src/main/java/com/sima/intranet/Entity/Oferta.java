package com.sima.intranet.Entity;

import com.sima.intranet.Enumarable.Gerencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String codigo;
    private String zona;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    @Column(columnDefinition = "TEXT")
    private String requisitos;
    @Column(columnDefinition = "TEXT")
    private String seOfrece;

    private Gerencia gerencia;
}
