package com.sima.intranet.Dto;

import com.sima.intranet.Entity.Capacitacion;
import com.sima.intranet.Entity.Credencial;
import com.sima.intranet.Enumarable.CursoHabilitante;
import com.sima.intranet.Enumarable.TipoCredencial;
import com.sima.intranet.Enumarable.TipoHabilitacion;
import com.sima.intranet.Util.Strings;
import com.sima.intranet.Util.UtilesFecha;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CredencialasDTO implements Serializable {
    private static final String ACTIVO = "Activo";
    private static final String INACTIVO = "Inactivo";
    private static final String SUSPENDIDO = "Suspendido";
    List<Habilitacion> habilitaciones = new ArrayList<>();
    List<Agente> agente = new ArrayList<>();

    List<Vigilador> vigilador = new ArrayList<>();
    List<CursoHabilitante> cursos = new ArrayList<>();
    Map<CursoHabilitante , LocalDate> cursosFecha = new HashMap<>();

    String numeroCredencial = "";


    public void completarInformacion(List<Credencial> credenciales){

        if(cursos.contains(CursoHabilitante.PSA001)){
            agregarHabilitiacionActiva(TipoHabilitacion.AGENTE_VIGILADOR);
            agregarHabilitiacionActiva(TipoHabilitacion.ASISTENCIA);
            agregarHabilitiacionActiva(TipoHabilitacion.PLATAFORMA);
        }else{
            agregarHabilitiacionInactiva(TipoHabilitacion.AGENTE_VIGILADOR);
            agregarHabilitiacionInactiva(TipoHabilitacion.ASISTENCIA);
            agregarHabilitiacionInactiva(TipoHabilitacion.PLATAFORMA);
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.PSA002))){
            agregarHabilitiacionActiva(TipoHabilitacion.OPERADOR_RAYOS_X);
        }else{
            agregarHabilitiacionInactiva(TipoHabilitacion.OPERADOR_RAYOS_X);
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.PSA005))){
            agregarHabilitiacionActiva(TipoHabilitacion.TERMINAL_CARGA);
        }else{
            agregarHabilitiacionInactiva(TipoHabilitacion.TERMINAL_CARGA);
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.PSA004))){
            agregarHabilitiacionActiva(TipoHabilitacion.SUPERVISOR);
        }else{
            agregarHabilitiacionInactiva(TipoHabilitacion.SUPERVISOR);
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.ANAC002))){
            agregarHabilitiacionActiva(TipoHabilitacion.CHOFER);
        }else{
            agregarHabilitiacionInactiva(TipoHabilitacion.CHOFER);
        }

        if(cursos.containsAll(List.of(CursoHabilitante.PSA001 , CursoHabilitante.ANAC001))){
            agregarHabilitiacionActiva(TipoHabilitacion.BRIGADISTA);
        }else{
            agregarHabilitiacionInactiva(TipoHabilitacion.BRIGADISTA);
        }
        for(CursoHabilitante c : CursoHabilitante.values()){
            String fecha = cursosFecha.get(c) != null ?  Strings.formatearFecha(cursosFecha.get(c)) : "#N/A";
                agente.add(
                        new Agente(
                                fecha,
                                c.name() ,
                                c.titulo));
        }

        if(credenciales != null && credenciales.size() > 0){
            Credencial cred = credenciales.get(0);
            Boolean esFisica = cred.getTipo().equals(TipoCredencial.CREDENCIAL_FISICA);
            if(esFisica){
                addVigilador("CREDENCIAL FISICA" , "SI");
                addVigilador("NOTA MINISTERIO" , "NO");
            }else{
                addVigilador("CREDENCIAL FISICA" , "NO");
                addVigilador("NOTA MINISTERIO" , "SI");
            }

            addVigilador( "GERENCIA" ,cred.getGerencia().descrip );
            addVigilador( "VENCIMIENTO" , Strings.formatearFecha(cred.getFechaVencimentoCredencial()));
            addVigilador("JURISDICCIÓN" , cred.getJurisdiccion().descrip);
            numeroCredencial = cred.getCodigoCredencial();
        }else{
            addVigilador("CREDENCIAL FISICA" , "#N/A");
            addVigilador("NOTA MINISTERIO" , "#N/A");
            addVigilador( "GERENCIA" ,"#N/A" );
            addVigilador( "VENCIMIENTO" , "#N/A");
            addVigilador("JURISDICCIÓN" , "#N/A");
            numeroCredencial = "#N/A";
        }
    }

    private void addVigilador(String nombre, String valor) {
        vigilador.add(new Vigilador(nombre , valor));
    }

    private void agregarHabilitiacionInactiva(TipoHabilitacion tipo) {
        habilitaciones.add(new Habilitacion(tipo.nombre, INACTIVO));
    }

    private void agregarHabilitiacionActiva(TipoHabilitacion tipo) {
        habilitaciones.add(new Habilitacion(tipo.nombre, ACTIVO ));
    }


    public void addCurso(Capacitacion c) {
        if(c.getFechaVencimentoCapacitacion()!= null){
            if( UtilesFecha.esFechaVigente(c.getFechaVencimentoCapacitacion())) {
                cursos.add(c.getTipoCurso());
            }
            cursosFecha.put(c.getTipoCurso() , c.getFechaVencimentoCapacitacion());
        }
    }


}
@Data
class Habilitacion{
    String nombre;
    String estado;

    public Habilitacion(String nombre, String estado) {
        this.estado = estado;
        this.nombre = nombre;
    }
}
@Data
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
@Data
class  Vigilador{
    String nombre;
    String valor;

    public Vigilador(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
}
