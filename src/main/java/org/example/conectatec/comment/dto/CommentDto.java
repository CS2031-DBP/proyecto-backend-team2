package org.example.conectatec.comment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.clubPost.dto.ClubPostDto;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.studentPost.dto.StudentPostDto;
import org.example.conectatec.utecServicesPost.dto.UtecServicesPostDto;

@Data
public class CommentDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 500)
    private String content;

    @Valid
    private StudentPostDto studentPost;

    @NotNull
    @Valid
    private StudentDto student;

    @Valid
    private ClubPostDto clubPost;

    @Valid
    private UtecServicesPostDto utecPost;
}
