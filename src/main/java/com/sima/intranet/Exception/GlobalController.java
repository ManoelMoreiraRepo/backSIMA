package com.sima.intranet.Exception;

import com.sima.intranet.Entity.Mensaje;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalController {
    @ExceptionHandler(ParametroInvalidoException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Mensaje resourceNotFoundException(ParametroInvalidoException ex) {
        Mensaje msj = new Mensaje(!ex.getMessage().isEmpty() ? ex.getMessage() : "El parametro es invalido.");
        return msj;
    }
}
