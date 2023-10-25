package com.sima.intranet.Enumarable;


import java.util.Optional;
public enum Empresa {
    GRUPO_SIMA("GRUPO SIMA"),
    LA_BIZANTINA("LA BIZANTINA"),
    GPS("GPS"),
    GLOBAL("GLOBAL"),
    ECOKLIN("ECOKLIN"),
    SIN_EMPRESA( "SIN EMPRESA");

    public String descripcion;
    Empresa(String descripcion){
        this.descripcion = descripcion;
    }

    public static Optional<Empresa> get(int ordinal){
        for(Empresa e : Empresa.values()){
            if(e.ordinal() == ordinal){
                return  Optional.of(e);
            }
        }
        return  Optional.empty();
    }
}
