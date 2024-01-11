package com.sima.intranet.Enumarable;

public enum TipoHabilitacion {
    AGENTE_VIGILADOR("AGENTE VIGILADOR"),
    OPERADOR_RAYOS_X("OPERADOR DE RAYOS X"),
    PLATAFORMA("PLATAFORMA"),
    CHOFER("CHOFER"),
    BRIGADISTA("BRIGADISTA"),
    TERMINAL_CARGA("TERMINAL CARGA"),
    ASISTENCIA("ASISTENCIA"),
    SUPERVISOR("SUPERVISOR"),
    CCTV("CCTV"),
    OTROS("OTROS");

    public String nombre;

    TipoHabilitacion(String nombre){
        this.nombre = nombre;
    }
}
