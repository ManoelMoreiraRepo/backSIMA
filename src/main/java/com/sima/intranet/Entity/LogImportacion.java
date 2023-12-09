package com.sima.intranet.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LogImportacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private String archivo;

    private LocalDateTime inicio;

    public LogImportacion(LocalDateTime inicio , String nombreArchivo){
        this.inicio = inicio;
        this.archivo = nombreArchivo;
        this.observaciones= "Inicio observaciones: \\n";
    }

    public LogImportacion() {

    }

    public void addMensaje(String msj){
        this.observaciones += ("  " + msj + "\\n");
    }
}
