package com.sima.intranet.Dto;

import com.sima.intranet.Filtro.Filtro;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class ApiResponse {
    Map<String , Object> content = new HashMap<>();
    Paginable pageable;
    public ApiResponse(Filtro filtro){
        this.pageable = new Paginable(filtro);
    }
}


