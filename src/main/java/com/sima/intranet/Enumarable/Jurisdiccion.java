package com.sima.intranet.Enumarable;

public enum Jurisdiccion {
    CABA("CABA"),
    PROVINCIA("PROVINCIA"),
    AEP("AEP"),
    EZE("EZE");
    public String descrip;
    Jurisdiccion (String descrip){
        this.descrip = descrip;
    }

    public static Jurisdiccion getJurisdiccion(String dato){
        if(dato == null || dato.isEmpty()){
            return null;
        }
        for(Jurisdiccion j : Jurisdiccion.values()){
            if(j.descrip.equalsIgnoreCase(dato)){
                return j;
            }
        }
        return  null;
    }
}

