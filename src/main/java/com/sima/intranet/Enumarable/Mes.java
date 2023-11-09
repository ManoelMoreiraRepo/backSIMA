package com.sima.intranet.Enumarable;

public enum Mes {
    ENERO("ENERO"),
    FEBRERO("FEBRERO"),
    MARZO("MARZO"),
    ABRIL("ABRIL"),
    MAYO("MAYO"),
    JUNIO("JUNIO"),
    JULIO("JULIO"),
    AGOSTO("AGOSTO"),
    SEPTIEMBRE("SEPTIEMBRE"),
    OCTUBRE("OCTUBRE"),
    NOVIEMBRE("NOVIEMBRE"),
    DICIEMBRE("DICIEMBRE");

    private String nombre;

    Mes(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
