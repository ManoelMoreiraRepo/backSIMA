package com.sima.intranet.Enumarable;

import java.util.Arrays;
import java.util.Optional;

public enum CursoHabilitante {
    PSA001("Vto Cred" , 4 , "VIGILADOR" ),
    PSA002("Vto RX" ,13 ,"OPERADOR DE RAYOS X"),
    PSA004("P004" , 14 , "CURSO SUPERVISOR"),
    PSA005("P005", 15 , "TERMINAL CARGA"),
    PSA011("P011" , 16 , "A.C.C.I"),
    ANAC001("BRIG" , 17 , "PREVENCION Y EXTINCION INCENDIOS EN AEROPUERTOS"),
    ANAC002("AMAN" , 18 , "TALLER AMAN");

    public String cabeceraImportacion;
    public int columna;

    public String titulo;

    CursoHabilitante(String cabeceraImportacion , int columna , String titulo){
        this.cabeceraImportacion = cabeceraImportacion;
        this.columna = columna;
        this.titulo = titulo;
    }

    public static Optional<CursoHabilitante> getParaImportacion(String dato){
        if(dato == null || dato.isEmpty()) {
            return Optional.empty();
        }
        String finalDato = dato.trim();
        return Arrays.stream(CursoHabilitante.values()).filter(e->e.cabeceraImportacion.equals(finalDato)).findFirst();
    }
}
