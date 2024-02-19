package com.sima.intranet.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import com.sima.intranet.Dto.NuevaCuentaDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${mailModerador}")
    private String mailModerador;

    private JavaMailSender mailSender;

    private static String RUTA_TEMPLATE_NUEVA_CUENTA = "src/main/template/nuevaSolicitud.html";
    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public boolean sendEmailNuevaCuenta(NuevaCuentaDTO cuenta) throws MessagingException, FileNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        //message.setFrom(new InternetAddress("matias@netegia.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, mailModerador);
        message.setSubject("Nueva solicitud de cuenta.");

        StringBuilder contenidoHTML = new StringBuilder();

        try {
            File archivo = new File(RUTA_TEMPLATE_NUEVA_CUENTA);
            FileReader lectorArchivo = new FileReader(archivo);
            BufferedReader lectorBuffer = new BufferedReader(lectorArchivo);

            String linea;
            while ((linea = lectorBuffer.readLine()) != null) {
                contenidoHTML.append(linea);
            }
            lectorBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("Error al intentar procesar el email.");
        }

        String contenido = contenidoHTML.toString();
        contenido = contenido.replace("${nombre}", cuenta.getNombre());
        contenido = contenido.replace("${apellido}", cuenta.getApellido());
        contenido = contenido.replace("${dni}", cuenta.getDni());
        contenido = contenido.replace("${correo}", cuenta.getEmail());
        message.setContent(contenido, "text/html; charset=utf-8");

        mailSender.send(message);
        return true;
    }
}
