package com.sima.intranet.Controller;

import com.sima.intranet.Entity.LogImportacion;
import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Interface.ImportacionInterface;
import com.sima.intranet.Repository.LogImportacionRepository;
import com.sima.intranet.Service.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/importacion")
public class ImportacionController {
    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ImportacionInterface importador;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private LogImportacionRepository logImportacionRepository;

    @PostMapping("/archivo")
    public ResponseEntity<String> handleCSVUpload(@RequestParam("file") MultipartFile file , HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No se pudo recibir el archivo.");
        }


        try {
            String originalFileName = file.getOriginalFilename();
            if(!originalFileName.endsWith(".xlsx")){
                throw new ParametroInvalidoException("Solo esta permitida la importación de archivos xlsx.");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = sdf.format(new Date());
            String newFileName = timestamp + "_" + originalFileName;

            Path targetPath = Path.of(uploadDirectory, newFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Usuario usuario = usuarioService.getUsuarioActivo(request).orElse(null);

            String mensaje = importador.reconocerFormatoImplementado(String.valueOf(targetPath) , newFileName , usuario);

            importador.procesarImportacion(String.valueOf(targetPath) , newFileName , usuario);

            return ResponseEntity.ok(mensaje);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al cargar el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/log")
    public ResponseEntity<LogImportacion> getPorId(@RequestParam Long id){
        var log = logImportacionRepository.findById(id);
        if(log.isPresent()){

            return ResponseEntity.ok(log.get());
        }else{
            throw new ParametroInvalidoException("No se encontro el log.");
        }
    }

    @GetMapping("/logs")
    public  ResponseEntity<List<LogImportacion>> getAll(){
        return ResponseEntity.ok(logImportacionRepository.findAllByOrderByInicioDesc());
    }

    @GetMapping("descargar/{nombre}")
    public ResponseEntity<Resource> download(@PathVariable("nombre") String nombre) throws IOException {
        File file = new File(uploadDirectory + File.separator + nombre);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=archivo.xlsx");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
