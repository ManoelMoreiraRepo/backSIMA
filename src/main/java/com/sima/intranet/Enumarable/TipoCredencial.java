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

    public static TipoCredencial getTipoCredencialImportacion(String esCredencialFisica){

        if(esCredencialFisica == null){
            return null;
        }

        if(esCredencialFisica.trim().equals("SI")){
            return TipoCredencial.CREDENCIAL_FISICA;
        }else if(esCredencialFisica.trim().equals("NO")){
            return  TipoCredencial.NOTA_MINISTERIO;
        }else{
            return null;
        }

    }


}
