package com.sima.intranet.Enumarable;

import java.util.List;

public enum Gerencias {

    GER_001 (List.of("") ),
    GER_002 (List.of("") ),
    GER_003 (List.of("") ),
    GER_004 (List.of("CANELA DARIO") ),
    GER_005 (List.of("") ),
    GER_006 (List.of("") );

    public List<String> supervisores;

    Gerencias(List<String> supervisores){
        this.supervisores = supervisores;
    }

   /* Contador por gerencia:
    cod:001 Grupo sima-Operacio central
    cod:002 La biza - Edu
    cod:003 Edenor - Casteli
    cod:004 Global- Canela
    cod:005 Aeropuertos-sosa
     cod:006 Ecoklin- sandra*/
}
