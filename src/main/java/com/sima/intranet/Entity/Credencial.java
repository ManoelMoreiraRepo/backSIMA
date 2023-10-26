package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.TipoCredencial;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Credencial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredencial;
    
    @Column
    private String NombreCredencial;
    
    @Column
    private String CodigoCredencial;
    
    @Column
    private  boolean EstadoCredencial;
    
    @Column
    private Date FechaOtorgamentoCredencial;
    
    @Column
    private Date FechaVencimentoCredencial;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idEmpleado")
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private Empleado empleado;

    private TipoCredencial tipo;

    private Gerencia gerencia;

    public Credencial(Empleado empleado, Date fechaCred, TipoCredencial tipoCred, Gerencia gerencia) {
        this.empleado = empleado;
        this.FechaVencimentoCredencial = fechaCred;
        this.tipo = tipoCred;
        this.gerencia = gerencia;
    }
}
