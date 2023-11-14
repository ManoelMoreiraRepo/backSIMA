package com.sima.intranet.Enumarable;

public enum EstadoDia {
    TURNO_MANANA("A"),
    TURNO_TARDE("B"),
    TURNO_NOCHE("C"),
    AUSENTE_ENFERMEDAD("AE"),
    SUSPENCION("SUSP"),
    CURSO("CUR"),
    LXF("LxF"),
    DXM("DXM"),
    DXE("DXE"),
    ART("ART"),
    VACACIONES("VV"),
    AUSENTE(""),
    LICENCIA_PERMANENTE("LP");

    public String sigla;

    EstadoDia(String sigla){
        this.sigla = sigla;
    }

    public static EstadoDia getEstadoDiaImportacion(String dato) {
        if(dato == null || dato.isBlank()){
            return null;
        }
        for(EstadoDia e : EstadoDia.values()){
            if(e.sigla.equalsIgnoreCase(dato.trim())){
                return e;
            }
        }
        return null;
    }
}
