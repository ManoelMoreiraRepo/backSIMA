package com.sima.intranet.Controller;

import com.sima.intranet.Dto.CredencialasDTO;
import com.sima.intranet.Entity.Credencial;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Jurisdiccion;
import com.sima.intranet.Enumarable.TipoCredencial;
import com.sima.intranet.Service.CredencialServiceImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER03, TipoCredencial.NOTA_MINISTERIO));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER04, TipoCredencial.NOTA_MINISTERIO));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER03, TipoCredencial.CREDENCIAL_FISICA));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.CABA , Gerencia.GER04, TipoCredencial.CREDENCIAL_FISICA));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER03, TipoCredencial.NOTA_MINISTERIO));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER04, TipoCredencial.NOTA_MINISTERIO));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER03, TipoCredencial.CREDENCIAL_FISICA));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.PROVINCIA , Gerencia.GER04, TipoCredencial.CREDENCIAL_FISICA));

        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.AEP , Gerencia.GER02, TipoCredencial.CREDENCIAL_FISICA));
        lista.add(iCredencial.getEstadisticaCredencial(LocalDate.now(),Jurisdiccion.EZE , Gerencia.GER02, TipoCredencial.CREDENCIAL_FISICA));

        return lista;
    }

    @GetMapping("/credencialesDTO/{id}")
    public ResponseEntity<CredencialasDTO> getInfoCredeciales(@PathVariable String id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(iCredencial.getCredencialesDTO(Long.parseLong(id)));
    }
}
