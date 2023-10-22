package com.sima.intranet.Enumarable;

import java.util.List;

public enum Gerencias {

    GER_001 ("ALFREDORAFA" , "G01" , "GRUPO SIMA"),
    GER_002 ("MATIASBASILOTTA" , "G02" , "AEROPUERTOS"),
    GER_003 ("LUCASGOMEZ" , "G03" , "GLOBAL"),
    GER_004 ("CANELADARIO" , "G04" , "EDENOR"),
    GER_005 ("ROMINALOPEZ" , "G05" , "LA BIZANTINA"),
    GER_006 ("MANOELGAFA"  , "G06" , "ECOKLIN");

    public String supervisor;
    public String descrip;
    public String codigo;

    Gerencias(String supervisor , String codigo , String descrip){
        this.supervisor = supervisor;
        this.codigo = codigo;
        this.descrip = descrip;
    }

    public static Gerencias getGerencia(String dato){
        for(Gerencias g : Gerencias.values()){
            if(g.supervisor.equalsIgnoreCase(dato.replace(" " , ""))){
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
