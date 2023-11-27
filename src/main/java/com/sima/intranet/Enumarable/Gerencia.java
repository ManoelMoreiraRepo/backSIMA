package com.sima.intranet.Enumarable;

public enum Gerencia {

    GER01( "G01" , "GRUPO SIMA" , "GROUP"  ),
    GER02( "G02" , "AEROPUERTOS" , "GPS" ),//GPS
    GER03( "G03" , "GLOBAL" , "GLOBAL" ),
    GER04( "G04" , "EDENOR" , "GLOBAL" ), // GLOBAL
    GER05( "G05" , "LA BIZANTINA" , "LA BIZANTINA" ),
    GER06( "G06" , "ECOKLIN" , "ECOKLIN" );

    public String descrip;
    public String codigo;

    public String codigoOfertasEmpleo;

    Gerencia(String codigo , String descrip , String codigoOfertasEmpleo){
        this.codigo = codigo;
        this.descrip = descrip;
        this.codigoOfertasEmpleo = codigoOfertasEmpleo;
    }

    public static Gerencia getGerencia(String dato){
        if(dato==null){
            return null;
        }
        for(Gerencia g : Gerencia.values()){
            if(g.codigo.equalsIgnoreCase(dato.replace(" " , ""))){
                return g;
            }
        }
        return null;
    }

    public static Gerencia getGerenciaParaImpOfertaEmpleo(String dato) {
        if(dato == null || dato.isBlank()){
            return null;
        }
        for(Gerencia g : Gerencia.values()){
            if(g.codigoOfertasEmpleo.equalsIgnoreCase(dato.trim())){
                return g;
            }
        }
        return null;

    }

    public String getCodigo() {
        return codigo;
    }
    /* Contador por gerencia:
    cod:001 Grupo sima-Operacio central
    cod:002 La biza - Edu
    cod:003 Edenor - Casteli
    cod:004 Global- Canela
    cod:005 Aeropuertos-sosa
     cod:006 Ecoklin- sandra*/
}
