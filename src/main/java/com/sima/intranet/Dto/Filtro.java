package com.sima.intranet.Dto;

import lombok.Data;

@Data
public class Filtro {
    private Object defecto;
    private Object filtro;

    public Filtro(Object defecto, Object filtro){
        this.filtro = filtro;
        this.defecto = defecto;
    }
}
