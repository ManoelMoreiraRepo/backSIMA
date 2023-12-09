package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Interface.ImportacionInterface;
import com.sima.intranet.Repository.LogImportacionRepository;
import com.sima.intranet.Service.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/importacion")
public class ImportacionController {
    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ImportacionInterface importador;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/archivo")
    public ResponseEntity<String> handleCSVUpload(@RequestParam("file") MultipartFile file , HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No se puedo recibir el archivo.");
        }


        try {
            String originalFileName = file.getOriginalFilename();
            if(originalFileName.endsWith(".xls")){
                throw new ParametroInvalidoException("La extension xls no esta permitida.");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = sdf.format(new Date());
            String newFileName = timestamp + "_" + originalFileName;

            Path targetPath = Path.of(uploadDirectory, newFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Usuario usuario = usuarioService.getUsuarioActivo(request).orElse(null);

            importador.procesarImportacion(String.valueOf(targetPath) , newFileName , usuario);

            return ResponseEntity.ok("Archivo cargado con éxito.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al cargar el archivo: " + e.getMessage());
        }
    }

}
