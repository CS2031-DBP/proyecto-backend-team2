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

    private Long id; // Este campo será generado por el sistema y no es necesario en el JSON de entrada

    @NotNull
    @Size(max = 500)
    private String content;

    @NotNull
    private Long studentId;

    private Long studentPostId;

    private Long clubPostId;

    private Long utecPostId;
}