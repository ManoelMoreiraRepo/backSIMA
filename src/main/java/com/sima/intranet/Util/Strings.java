package com.sima.intranet.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Strings {

    public static Boolean isNullOrEmpty(String dato){
        if(dato == null || dato.isEmpty()){
            return true;
        }
        return false;
    }

    public static Boolean isNotNullOrEmpty(String dato){
        if(dato != null && !dato.isEmpty()){
            return true;
        }
        return false;
    }

    public static String formatearFecha(LocalDate fecha){
        if(fecha == null)
            return "";

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formato.format(fecha);
    }
}
