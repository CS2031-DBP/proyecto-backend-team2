package org.example.conectatec.career.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CareerDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String nombre;

    @NotNull
    @Size(min = 2, max = 50)
    private String facultad;
}
