package com.sima.intranet.Enumarable;

import java.util.Arrays;

public enum Sindicato {
    UPADEP("U.P.A.D.E.P."),
    SUVICO("S.U.V.I.C.O."),
    SUTCA("S.U.T.C.A."),
    SUTCAPRA("S.U.T.C.A.P.R.A."),
    SOM("S.O.M."),
    UPSRA("U.P.S.R.A.");

    public String descrip;
    Sindicato(String descip){
        this.descrip = descip;
    }
    public static Sindicato getSindicato(String sindicato){
        for(Sindicato s : Sindicato.values()){
            if(s.descrip.equalsIgnoreCase(sindicato.replace(" " , ""))){
                return s;
            }
        }
        return null;
    }

}
