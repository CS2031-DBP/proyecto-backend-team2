package org.e2e.e2e.auth.dto;

import lombok.Data;

@Data
public class LoginReq {
    String email;
    String password;
}
