package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Mensaje;
import com.sima.intranet.Entity.Oferta;
import com.sima.intranet.Entity.Postulacion;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Interface.OfertaInterface;
import com.sima.intranet.Interface.PostulacionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/oferta")
public class OfertaController {
    @Autowired
    private OfertaInterface ofertaService;

    @Autowired
    private PostulacionInterface postulacionService;

    @Value("${upload.cv}")
    private String uploadCV;

    @GetMapping("/listar")
    public Page<Oferta> listarOfertasPaginable(@PageableDefault(size = 20 ) Pageable pageable , @RequestParam String ordenado , @RequestParam String orden ) {
        if(!ordenado.isEmpty() && !orden.isEmpty()){
            if(orden.equals("ASC")){
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, ordenado));
            }else{
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, ordenado));
            }

        }

        return ofertaService.getOfertas(pageable);
    }

    @GetMapping("/{id}")
    public Oferta getOferta(@PathVariable Long id){
        return ofertaService.getOferta(id).orElseThrow(() -> new ParametroInvalidoException("Ocurrio un error."));
    }

    @PostMapping("/archivo")
    public ResponseEntity<String> handleCVUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No se puedo recibir el archivo.");
        }

        String originalFileName = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String newFileName = timestamp + "_" + originalFileName;

        Path targetPath = Path.of(uploadCV, newFileName);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return ResponseEntity.ok(newFileName);
    }

    @PostMapping("/postular")
    public ResponseEntity<Mensaje> recibirPostulacion(@RequestBody Postulacion postulacion){
        postulacionService.guardarPostulacion(postulacion);
        return ResponseEntity.ok().body(new Mensaje("Postulacion recibida correctamente"));
    }

}
