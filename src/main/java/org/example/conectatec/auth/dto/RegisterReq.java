package org.example.conectatec.auth.dto;


import lombok.Data;

@Data
public class RegisterReq {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;

}
