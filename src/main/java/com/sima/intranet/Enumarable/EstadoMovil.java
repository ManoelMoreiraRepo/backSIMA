package com.sima.intranet.Enumarable;

public enum EstadoMovil {
    ACTIVO("ACTIVO"),
    NUEVA("NUEVA"),
    TALLER("TALLER"),
    JUDICIAL("JUDICIAL"),
    BAJA("BAJA");

    public String descripcion;

    EstadoMovil(String descripcion){
        this.descripcion = descripcion;
    }

    public static EstadoMovil getEstadoMovilImportacion ( String descrip){
        if(descrip == null){
            return null;
        }

        for (EstadoMovil e : EstadoMovil.values()){
            if(e.descripcion.equalsIgnoreCase(descrip.trim())){
                return e;
            }
        }
        return null;
    }
}
