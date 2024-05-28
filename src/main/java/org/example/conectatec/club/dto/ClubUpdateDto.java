package org.example.conectatec.club.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;

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
    @Valid
    private CareerDto career;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;
}
