package com.sima.intranet.Service;

import com.sima.intranet.Dto.Filtro;
import com.sima.intranet.Dto.FiltroSelect;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.TipoFiltro;
import com.sima.intranet.Interface.Filtrable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiltroServiceImpl {

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
}
