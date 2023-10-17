package com.sima.intranet.Enumarable;

import java.util.Optional;

public enum Empresas {
    GRUPO_SIMA,
    LA_BIZANTINA,
    GPS,
    GLOBAL,
    ECOKLIN;

    Empresas(){

    }

    public static Optional<Empresas> get(int ordinal){
        for(Empresas e : Empresas.values()){
            if(e.ordinal() == ordinal){
                return  Optional.of(e);
            }
        }
        return  Optional.empty();
    }
}
