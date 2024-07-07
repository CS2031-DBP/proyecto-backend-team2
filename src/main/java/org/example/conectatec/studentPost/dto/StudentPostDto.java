package org.example.conectatec.studentPost.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.comment.domain.Comment;
import java.util.List;

@Data
public class StudentPostDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 100)
    private String hashtag;

    @NotNull
    private String media;

    @NotNull
    @Size(max = 500)
    private String caption;

    @NotNull
    @Valid
    private List<Comment> commentBox;

}
