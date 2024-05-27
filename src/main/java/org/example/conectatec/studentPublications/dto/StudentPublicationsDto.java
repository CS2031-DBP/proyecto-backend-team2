package org.example.conectatec.studentPublications.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.commentBox.dto.CommentBoxDto;
import org.example.conectatec.student.dto.StudentDto;

import java.util.List;

@Data
public class StudentPublicationsDto {
    @NotNull
    @Size(max = 100)
    private String hashtag;

    @NotNull
    private String media;

    @NotNull
    @Size(max = 500)
    private String answer;

    @NotNull
    @Valid
    private List<CommentBoxDto> commentBoxUser;

    @NotNull
    @Valid
    private StudentDto student;
}
