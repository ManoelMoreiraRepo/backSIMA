package com.sima.intranet.Enumarable;

public enum TipoCredencial {
    NOTA_MINISTERIO("NOTA"),
    CREDENCIAL_FISICA("FISICA");

    public String descripcion;
    TipoCredencial(String descripcion){
        this.descripcion=descripcion;
    }

    public static TipoCredencial getTipoCredencial(String descripcion){
        for (TipoCredencial tipo:
              TipoCredencial.values()) {
            if(tipo.descripcion.equalsIgnoreCase(descripcion.trim())){
                return tipo;
            }
        }
        return null;
    }
}
