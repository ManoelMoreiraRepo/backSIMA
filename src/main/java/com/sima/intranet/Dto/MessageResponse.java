package com.sima.intranet.Dto;

import lombok.Data;

@Data
public class MessageResponse {
    public String message;
    public MessageResponse(String msj){
        this.message = msj;
    }
}
