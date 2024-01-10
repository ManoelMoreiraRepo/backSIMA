package com.sima.intranet.Enumarable;

import java.util.Arrays;
import java.util.Optional;

public enum CursoHabilitante {
    PSA001("Vto Cred" , 4),
    PSA002("Vto RX" ,13 ),
    PSA004("P004" , 14 ),
    PSA005("P005", 15),
    PSA011("P011" , 16),
    ANAC001("BRIG" , 17),
    ANAC002("AMAN" , 18);

    public String cabeceraImportacion;
    public int columna;

    CursoHabilitante(String cabeceraImportacion , int columna){
        this.cabeceraImportacion = cabeceraImportacion;
        this.columna = columna;
    }

    public static Optional<CursoHabilitante> getParaImportacion(String dato){
        if(dato == null || dato.isEmpty()) {
            return Optional.empty();
        }
        String finalDato = dato.trim();
        return Arrays.stream(CursoHabilitante.values()).filter(e->e.cabeceraImportacion.equals(finalDato)).findFirst();
    }
}
