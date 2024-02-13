package com.sima.intranet.Service;

import com.sima.intranet.Dto.Filtro;
import com.sima.intranet.Dto.FiltroSelect;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.TipoFiltro;
import com.sima.intranet.Interface.Filtrable;
import com.sima.intranet.Repository.EmpleadoRepository;
import com.sima.intranet.Repository.IndumentariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FiltroServiceImpl {


    private EmpleadoRepository empleadoRepository;
    private IndumentariaRepository indumentariaRepository;
    @Autowired
    public FiltroServiceImpl(EmpleadoRepository empleadoRepository , IndumentariaRepository indumentariaRepository){
        this.empleadoRepository = empleadoRepository;
        this.indumentariaRepository = indumentariaRepository;
    }

    public FiltroSelect[] getFiltroSelect(Filtrable[] array, Boolean seleccione){
        FiltroSelect[] selects = null;
        int i = 0;
        if(seleccione){
            selects = new FiltroSelect[array.length+1];
            selects[i++] = FiltroSelect.builder()
                    .value("")
                    .vista("Seleccione..")
                    .build();
        }else{
            selects = new FiltroSelect[array.length];
        }
        for(Filtrable item : array){
            selects[i++] = FiltroSelect.builder()
                    .value(item.getId())
                    .vista(item.getDescipcion())
                    .build();
        }
        return selects;
    }


    public Filtro procesarFiltroSelect(TipoFiltro tipo, Boolean seleccione) {
        FiltroSelect[] array = List.of().toArray(new FiltroSelect[0]);
       switch (tipo){
           case GERENCIA :
               array = getFiltroSelect(Gerencia.values(), seleccione);
               break;


       }

       return new Filtro( "" , array);

    }

    public FiltroSelect[] getSelect2Objetivo(String texto){
        Object[] resultado = empleadoRepository.getSelect2Objetivo(texto.toLowerCase());
        return getArrayGenerico(resultado);
    }

    public FiltroSelect[] getSelect2Familia(String texto){
        Object[] resultado = indumentariaRepository.getSelect2Familia(texto.toLowerCase());
        return getArrayGenerico(resultado);
    }

    public FiltroSelect[] getSelect2Producto(String texto){
        Object[] resultado = indumentariaRepository.getSelect2Producto(texto.toLowerCase());
        return getArrayGenerico(resultado);
    }

    public FiltroSelect[] getSelect2Modelo(String texto){
        Object[] resultado = indumentariaRepository.getSelect2Modelo(texto.toLowerCase());
        return getArrayGenerico(resultado);
    }

    private FiltroSelect[] getArrayGenerico(Object[] resultado){
        FiltroSelect[] array = new FiltroSelect[resultado.length];
        Integer i = 0;
        for (Object dato : resultado){
            array[i++] = new FiltroSelect(dato,dato);
        }
        return array;
    }


}
