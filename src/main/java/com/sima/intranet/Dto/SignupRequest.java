package com.sima.intranet.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    public Long id;
    public String username;
    public String email;
    public String password;
    public Set<String> role;
    public Set<String> getRole() {
        return this.role;
    }
}
