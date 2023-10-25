package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sima.intranet.Enumarable.Empresa;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Sindicato;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    
    @Column(name = "estadoEmpleado")
    private String estadoEmpleado;

    @Column(name = "DNIEmpleado")
    private String DNIEmpleado;

    @Column(name = "nombreEmpleado")
    private String nombreEmpleado;

    @Column(name = "apellidoEmpleado")
    private String apellidoEmpleado;

    @Column(name = "legajoEmpleado")
    private String legajoEmpleado;

    @Column(name = "fechaNascimentoEmpleado")
    private Date fechaNascimentoEmpleado;

    @Column(name = "fechaAltaEmpleado")
    private Date fechaAltaEmpleado;

    @Column(name = "objetivoEmpleado")
    private String objetivoEmpleado;

    @Column(name = "turnoEmpleado")
    private String turnoEmpleado;

    @Column(name = "telefonoEmpleado")
    private String telefonoEmpleado;

    @Column(name = "cargoEmpleado")
    private String cargoEmpleado;

    @Column(name = "emailEmpleado")
    private String emailEmpleado;

    @Column(name = "direccionEmpleado")
    private String direccionEmpleado;

    @Column(name = "codigoPostalEmpleado")
    private String codigoPostalEmpleado;

    private BigDecimal sueldoTotal;

    private Empresa empresa;

    private Gerencia gerencia;

    private Sindicato sindicato;
    
    //Indumentaria Del Empleado
    @OneToMany(mappedBy="empleado",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    ,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private List<Indumentaria> indumentaria;
    
    //Credenciales Del Empleado
    @OneToMany(mappedBy="empleado",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    ,fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private List<Credencial> credencial;
    
    //Capacitacion Del Empleado
    @OneToMany(mappedBy="empleado",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    ,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private List<Capacitacion> capacitacion;
    
    //Grilla Del Empleado    
    @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
      @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private Operacion operacion;
    
    /*
    @OneToOne(mappedBy="cliente",fetch = FetchType.LAZY)
    private List<Objetivo> objetivo;
    */

    public Empleado(String dni){
        this.DNIEmpleado = dni;
    }
}