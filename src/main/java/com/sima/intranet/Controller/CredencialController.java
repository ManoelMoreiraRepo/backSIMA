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

    @GetMapping("/cantidad")
    public List<List<Long>> getEstadisticaCredenciales(){
        List lista = new ArrayList<>();
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER_003 , TipoCredencial.NOTA_MINISTERIO));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER_004 , TipoCredencial.NOTA_MINISTERIO));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER_003 , TipoCredencial.CREDENCIAL_FISICA));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER_004 , TipoCredencial.CREDENCIAL_FISICA));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER_003 , TipoCredencial.NOTA_MINISTERIO));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER_004 , TipoCredencial.NOTA_MINISTERIO));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER_003 , TipoCredencial.CREDENCIAL_FISICA));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER_004 , TipoCredencial.CREDENCIAL_FISICA));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.AEP , Gerencia.GER_002 , TipoCredencial.CREDENCIAL_FISICA));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.EZE , Gerencia.GER_002 , TipoCredencial.CREDENCIAL_FISICA));

        return lista;
    }
}
