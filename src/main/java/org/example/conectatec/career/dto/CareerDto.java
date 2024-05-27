package org.example.conectatec.career.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.student.dto.StudentDto;

import java.util.List;

@Data
public class CareerDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String facultad;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Valid
    private List<StudentDto> students;
}
