package com.sima.intranet.Config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;

@ControllerAdvice
@CrossOrigin(origins = "*")
public class GlobalControllerAdvice {
    // Puedes agregar métodos adicionales aquí si es necesario
}