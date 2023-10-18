package com.sima.intranet.Enumarable;

import lombok.Data;

import java.util.Optional;
public enum Empresas {
    GRUPO_SIMA("GRUPO SIMA"),
    LA_BIZANTINA("LA BIZANTINA"),
    GPS("GPS"),
    GLOBAL("GLOBAL"),
    ECOKLIN("ECOKLIN"),
    SIN_EMPRESA( "SIN EMPRESA");

    public String descripcion;
    Empresas(String descripcion){
        this.descripcion = descripcion;
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
