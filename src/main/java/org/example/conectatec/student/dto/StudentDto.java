package org.example.conectatec.student.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.example.conectatec.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDto extends UserDto {
    @NotNull
    @Valid
    private CareerDto career;
    @NotNull
    @Valid
    private StudentFeedDto studentFeed;

    @NotNull
    @Valid
    private List<StudentFeedDto> publications = new ArrayList<>();
}
