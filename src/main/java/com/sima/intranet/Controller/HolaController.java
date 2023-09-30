 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sima.intranet.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mmnmo
 */
@RestController
public class HolaController {
    
    @GetMapping("/hola")
    public String dicirHola(){
    
        return "<h1> ♥♥♥♥♥♥ Hola Intranet Grupo Sima ♥♥♥♥♥♥♥♥  </h1>";
    }
    
}
