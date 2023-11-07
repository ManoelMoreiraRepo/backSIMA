package com.sima.intranet.Enumarable;

public enum Gerencia {

    GER_001 ( "G01" , "GRUPO SIMA"),
    GER_002 ( "G02" , "AEROPUERTOS"),//GPS
    GER_003 ( "G03" , "GLOBAL"),
    GER_004 ( "G04" , "EDENOR"), // GLOBAL
    GER_005 ( "G05" , "LA BIZANTINA"),
    GER_006 ( "G06" , "ECOKLIN");

    public String descrip;
    public String codigo;

    Gerencia(String codigo , String descrip){
        this.codigo = codigo;
        this.descrip = descrip;
    }

    public static Gerencia getGerencia(String dato){
        for(Gerencia g : Gerencia.values()){
            if(g.codigo.equalsIgnoreCase(dato.replace(" " , ""))){
                return g;
            }
        }
        return null;
    }

   /* Contador por gerencia:
    cod:001 Grupo sima-Operacio central
    cod:002 La biza - Edu
    cod:003 Edenor - Casteli
    cod:004 Global- Canela
    cod:005 Aeropuertos-sosa
     cod:006 Ecoklin- sandra*/
}
