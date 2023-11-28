package com.sima.intranet.Util;

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
}
