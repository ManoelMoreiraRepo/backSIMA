package com.sima.intranet.Service;

import com.sima.intranet.Entity.Postulacion;
import com.sima.intranet.Interface.PostulacionInterface;
import com.sima.intranet.Repository.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostulacionServiceImpl implements PostulacionInterface {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Override
    public void guardarPostulacion(Postulacion postulacion) {
        this.postulacionRepository.save(postulacion);
    }
}
