package org.example.conectatec.auth.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String name;
    private String email;
    private String password;
    private String role; // Puede ser "STUDENT", "CLUB", "UTEC"

    // Campos específicos según el tipo de usuario
    private Long careerId; // Para Student y Club
    private Long utecServiceId; // Para UTEC
}
