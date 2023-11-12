package com.sima.intranet.Enumarable;

public enum EstadoInfraccion {
    DESCONTADA("DESCONTADA"),
    ADEUDADA("ADEUDADA");

    public String descripcion;

    EstadoInfraccion(String descripcion){
        this.descripcion = descripcion;
    }

    public static EstadoInfraccion getEstadoParaImportacion(String dato){
        if(dato == null){
            return EstadoInfraccion.ADEUDADA;
        }

        for(EstadoInfraccion e : EstadoInfraccion.values()){
            if(e.descripcion.equalsIgnoreCase(dato.trim())){
                return e;
            }
        }
        return null;
    }
}
