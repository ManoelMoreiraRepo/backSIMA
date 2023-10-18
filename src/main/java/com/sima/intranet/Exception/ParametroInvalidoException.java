package com.sima.intranet.Exception;


public class ParametroInvalidoException extends  RuntimeException{
    private String mensaje;
    public ParametroInvalidoException(String mensaje){
        super(mensaje);
        this.mensaje=mensaje;
    }
}
