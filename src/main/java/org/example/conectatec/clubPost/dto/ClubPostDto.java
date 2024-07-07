package org.example.conectatec.clubPost.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.comment.dto.CommentDto;

import java.util.List;

@Data
public class ClubPostDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 500)
    private String caption;

    @NotNull
    private String media;

    @NotNull
    @Valid
    private CareerDto career;

    @NotNull
    @Valid
    private List<CommentDto> comments;
}
