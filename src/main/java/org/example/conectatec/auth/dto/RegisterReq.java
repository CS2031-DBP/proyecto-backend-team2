package org.example.conectatec.auth.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String name;
    private String email;
    private String password;
    private String role;
    private Long careerId;
    private Long utecServiceId;
}
