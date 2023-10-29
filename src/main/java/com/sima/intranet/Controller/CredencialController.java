package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Credencial;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Jurisdiccion;
import com.sima.intranet.Enumarable.TipoCredencial;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Service.CredencialServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Credencial")
public class CredencialController {

    @Autowired
    private CredencialServiceImpl iCredencial;

    @GetMapping("/traer")
    public Iterable<Credencial> getCredenciales() {
        return iCredencial.getAll();
    }

    @GetMapping("/traer/{id}")
    public Credencial getCredencial(@PathVariable long id) {
        return iCredencial.findCredencialByID(id);
    }

    @PostMapping("/nueva")
    public String createCredencial(@RequestBody Credencial credencial) {
        this.iCredencial.newCredencial(credencial);
        return "La Credencial fue creada correctamente";
    }

    @DeleteMapping
    public void deleteCredencial(@PathVariable long id) {
        iCredencial.deleteCredencial(id);

    }

    @PutMapping("/actualizar/{id}")
    public Credencial updateCredencial(@RequestBody Credencial credencial) {
        return this.iCredencial.modifyCredencial(credencial);
    }

    @GetMapping("/detail/{empleadoId}")
    public List<Credencial> findCredencialByIdEmpleado(@PathVariable("empleadoId") long id) {
        return iCredencial.listByIdEmpleado(id);
    }

    @PostMapping("/cantidad")
    public List<Long> getCoutEstadisticaJurisdiccionGerencia(@RequestBody Map<String,String> body){

        Jurisdiccion jurisdiccion = Jurisdiccion.valueOf(body.get("jurisdiccion"));
        Gerencia gerencia = Gerencia.getGerencia(body.get("gerencia"));
        TipoCredencial tipo = TipoCredencial.getTipoCredencial(body.get("tipo"));
        if(jurisdiccion == null || gerencia == null || tipo == null){
            throw new ParametroInvalidoException("Los parametros son invalidos");
        }
        Long vencidas = iCredencial.getCantidadCredencialesConVencimientoAnteriorA(LocalDate.now() , jurisdiccion , gerencia , tipo);
        Long proxMes = iCredencial.getCantidadCredencialesConVecimientoEntre(LocalDate.now() , LocalDate.now().plusMonths(1) , jurisdiccion , gerencia , tipo);
        Long prox3Meses = iCredencial.getCantidadCredencialesConVecimientoEntre(LocalDate.now().plusMonths(1) , LocalDate.now().plusMonths(4) , jurisdiccion , gerencia , tipo);
        return List.of(vencidas , proxMes , prox3Meses);
    }
}
