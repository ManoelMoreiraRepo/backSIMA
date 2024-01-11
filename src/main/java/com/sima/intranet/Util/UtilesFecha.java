package com.sima.intranet.Util;

import java.time.LocalDate;

public class UtilesFecha {
    public static Boolean esFechaVigente(LocalDate fecha){
        return fecha.isAfter(LocalDate.now());
    }
}
