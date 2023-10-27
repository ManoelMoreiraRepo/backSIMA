package com.sima.intranet.Dto;


import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String username;

    private String email;
    private String role;

    public UserInfoResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UserInfoResponse() {

    }
}
