package org.example.conectatec.auth.dto;

import lombok.Data;

@Data
public class AdminRegisterReq  {
    private String name;
    private String email;
    private String password;
    private String secretKey; // Clave secreta para validación

}
