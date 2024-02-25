package com.sima.intranet.Enumarable;

import lombok.NoArgsConstructor;

import java.lang.annotation.Target;

public enum EstadoDia {
    MAÑANA("A"),
    TARDE("B"),
    NOCHE("C"),
    AUSENTE_CON_AVISO("ACA"),
    AUSENTE_SIN_AVISO("ASA"),
    ART("ART"),
    CAMBIO_DE_GUARDIA("CXT"),
    CURSO("CUR"),
    CUSTODIA("Z"),
    DIA_POR_MUDANZA("DXM"),
    DIA_POR_EXAMEN("DXE"),
    LICENCIA_PROLONGADA("LXP"),
    LICENCIA_PERMANENTE("LP"),
    LICENCIA_POR_FALLECIMENTO("LXF"),
    FRANCO(""),
    SUSPENCION("SUSP"),
    VACACIONES("VV");

    public String sigla;

    EstadoDia(String sigla){
        this.sigla = sigla;
    }

    public static EstadoDia getEstadoDiaImportacion(String dato) {
        if(dato == null || dato.isBlank()){
            return FRANCO;
        }
        for(EstadoDia e : EstadoDia.values()){
            if(e.sigla.equalsIgnoreCase(dato.trim())){
                return e;
            }
        }
        return null;
    }

    public boolean isDiaTrabajado(){
        return this.equals(MAÑANA) || this.equals(TARDE) || this.equals(NOCHE);
    }
}
