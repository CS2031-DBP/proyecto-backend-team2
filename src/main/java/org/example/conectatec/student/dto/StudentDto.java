package org.example.conectatec.student.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;

@Data
public class StudentDto {
    @NotNull
    @Valid
    private CareerDto carrera;
}
