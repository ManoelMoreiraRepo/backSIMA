package com.sima.intranet.Enumarable;


import java.util.List;
import java.util.Optional;
public enum Empresa {
    COM01("GPS" , List.of(Gerencia.GER01, Gerencia.GER02, Gerencia.GER03)),
    COM02("GLOBAL" , List.of(Gerencia.GER01 , Gerencia.GER02 , Gerencia.GER03 , Gerencia.GER04)),
    COM03("LA BIZANTINA" , List.of(Gerencia.GER05)),
    COM04("ECOKLIN" , List.of(Gerencia.GER06)),
    COM05("HUB" , List.of()),
    SIN_EMPRESA( "SIN EMPRESA" , List.of());

    public String descripcion;

    public List<Gerencia> gerencias;

    Empresa(String descripcion , List<Gerencia> gerencias ){
        this.descripcion = descripcion;
        this.gerencias = gerencias;
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
