package org.example.conectatec.club.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClubUpdateDto {
    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 50)
    private String carrera;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;
}
