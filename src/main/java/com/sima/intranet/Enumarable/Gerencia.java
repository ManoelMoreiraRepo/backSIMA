package com.sima.intranet.Enumarable;

public enum Gerencia {

    GER_001 ( "G01" , "GRUPO SIMA" , "GROUP" , Empresa.GRUPO_SIMA ),
    GER_002 ( "G02" , "AEROPUERTOS" , "GPS" , Empresa.GPS),//GPS
    GER_003 ( "G03" , "GLOBAL" , "GLOBAL" , Empresa.GLOBAL),
    GER_004 ( "G04" , "EDENOR" , "GLOBAL" , Empresa.GLOBAL), // GLOBAL
    GER_005 ( "G05" , "LA BIZANTINA" , "LA BIZANTINA" , Empresa.LA_BIZANTINA),
    GER_006 ( "G06" , "ECOKLIN" , "ECOKLIN",Empresa.ECOKLIN);

    public String descrip;
    public String codigo;

    public String codigoOfertasEmpleo;

    public Empresa empresa;

    Gerencia(String codigo , String descrip , String codigoOfertasEmpleo , Empresa empresa){
        this.codigo = codigo;
        this.descrip = descrip;
        this.codigoOfertasEmpleo = codigoOfertasEmpleo;
        this.empresa = empresa;
    }

    public static Gerencia getGerencia(String dato){
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

   /* Contador por gerencia:
    cod:001 Grupo sima-Operacio central
    cod:002 La biza - Edu
    cod:003 Edenor - Casteli
    cod:004 Global- Canela
    cod:005 Aeropuertos-sosa
     cod:006 Ecoklin- sandra*/
}
