package com.sima.intranet.Dto;

import com.sima.intranet.Enumarable.CursoHabilitante;
import com.sima.intranet.Enumarable.TipoHabilitacion;
import com.sima.intranet.Util.Strings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CredencialasDTO {
    private static final String ACTIVO = "Activo";
    private static final String INACTIVO = "Inactivo";
    private static final String SUSPENDIDO = "Suspendido";
    List<Habilitacion> habilitaciones = new ArrayList<>();
    List<Agente> agente = new ArrayList<>();
    List<Vigilador> vigilador = new ArrayList<>();
    List<CursoHabilitante> cursos = new ArrayList<>();
    Map<CursoHabilitante , LocalDate> cursosFecha = new HashMap<>();

    Map<TipoHabilitacion, String> tipoHabilitacionEstado = new HashMap<>();
    String numeroCredencial = "";


    public void completarInformacion(){

        if(cursos.contains(CursoHabilitante.PSA001)){
            habilitaciones.add(new Habilitacion(TipoHabilitacion.AGENTE_VIGILADOR.nombre, ACTIVO));
            habilitaciones.add(new Habilitacion(TipoHabilitacion.ASISTENCIA.nombre, ACTIVO));
            habilitaciones.add(new Habilitacion(TipoHabilitacion.PLATAFORMA.nombre, ACTIVO));
        }else{
            habilitaciones.add(new Habilitacion(TipoHabilitacion.AGENTE_VIGILADOR.nombre, INACTIVO));
            habilitaciones.add(new Habilitacion(TipoHabilitacion.ASISTENCIA.nombre, INACTIVO));
            habilitaciones.add(new Habilitacion(TipoHabilitacion.PLATAFORMA.nombre, INACTIVO));
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.PSA002))){
            habilitaciones.add(new Habilitacion(TipoHabilitacion.OPERADOR_RAYOS_X.nombre, ACTIVO));
        }else{
            habilitaciones.add(new Habilitacion(TipoHabilitacion.OPERADOR_RAYOS_X.nombre, INACTIVO));
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.PSA002))){
            habilitaciones.add(new Habilitacion(TipoHabilitacion.OPERADOR_RAYOS_X.nombre, ACTIVO));
        }else{
            habilitaciones.add(new Habilitacion(TipoHabilitacion.OPERADOR_RAYOS_X.nombre, INACTIVO));
        }


        for(CursoHabilitante c : CursoHabilitante.values()){
                agente.add(
                        new Agente(
                                Strings.formatearFecha(cursosFecha.get(c)),
                                CursoHabilitante.PSA001.name() ,
                                CursoHabilitante.PSA001.titulo));
        }
    }



}
class Habilitacion{
    String nombre;
    String estado;

    public Habilitacion(String nombre, String estado) {
        this.estado = estado;
        this.nombre = nombre;
    }
}
class Agente{
    String fecha;
    String codigo;
    String nombre;

    public Agente(String s, String name, String titulo) {
        this.fecha = s;
        this.codigo = name;
        this.nombre = titulo;
    }
}
class  Vigilador{
    String nombre;
    String valor;
}
