package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    private Usuario usuario;

    public LogImportacion(LocalDateTime inicio , String nombreArchivo , Usuario usuario){
        this.inicio = inicio;
        this.archivo = nombreArchivo;
        this.usuario = usuario;
        this.observaciones= "Inicio observaciones: <br/>";
    }

    public LogImportacion() {

    }

    public void addMensaje(String msj){
        this.observaciones += ("  " + msj + "<br/>");
    }
}
