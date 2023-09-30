package com.sima.intranet.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Entity
@Data

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
    private Long telefonoEmpleado;

    @Column(name = "cargoEmpleado")
    private String cargoEmpleado;

    @Column(name = "emailEmpleado")
    private String emailEmpleado;

    @Column(name = "direccionEmpleado")
    private String direccionEmpleado;

    @Column(name = "codigoPostalEmpleado")
    private String codigoPostalEmpleado;
    
    
    @OneToMany(mappedBy="empleado",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    ,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"empleado","hibernateLazyInitializer","handler"})
    private List<Indumentaria> indumentaria;
    
     /*@OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL)
    private Operacion operacion;
    
   public void agregarIdumentaria(Indumentaria laIndumentaria){
    
    if(laIndumentaria == null)  indumentaria = new ArrayList<>();
    laIdumentaria.setCliente(this);
    }
    
    @OneToMany(mappedBy="cliente",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Objetivo> objetivo;*/
    
}