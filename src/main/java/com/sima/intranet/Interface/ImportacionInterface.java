package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Usuario;

public interface ImportacionInterface {

    void procesarImportacion(String ruta, String newFileName, Usuario usuario);
}
