package com.sima.intranet.Enumarable;

import java.util.List;

public enum Sindicato {
    UPADEP(List.of("U.P.A.D.E.P."), "UPADEP"),
    SUVICO(List.of("S.U.V.I.C.O."), "SUVICO"),
    UPSRA(List.of("U.P.S.R.A."), "UPSRA"),
    SOM(List.of("S.O.M." , "Sndicato Obreros Maestranza"), "SOM"),
    SIN_SINDICATO(List.of("SIN SINDICATO"), "SIN SINDICATO");


    public List<String> descrip;

    public String titulo;
    Sindicato(List<String> descip , String titulo){
        this.titulo = titulo;
        this.descrip = descip;
    }
    public static Sindicato getSindicatoImportacion(String sindicato){
        if(sindicato == null || sindicato.isEmpty()){
            return  SIN_SINDICATO;
        }
        for(Sindicato s : Sindicato.values()){
            for(String e : s.descrip){
                if(e.equalsIgnoreCase(sindicato.trim())){
                    return s;
                }
            }

        }
        return null;
    }

    public static Sindicato getSindicato(String sindicato){
        if(sindicato == null || sindicato.isEmpty()){
            return  null;
        }
        for(Sindicato s : Sindicato.values()){
            if(s.name().equalsIgnoreCase(sindicato)){
                return s;
            }
        }
        return null;
    }
}
