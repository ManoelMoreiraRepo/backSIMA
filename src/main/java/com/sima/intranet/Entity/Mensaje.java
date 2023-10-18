
package com.sima.intranet.Entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Mensaje {
    
    private String mensaje;
    
    //Constructor

    public Mensaje() {
    }

    public Mensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    //Getters y Setters

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
