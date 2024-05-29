package org.example.conectatec.studentFeed.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.commentBox.domain.CommentBox;
import java.util.List;

@Data
public class StudentFeedDto {
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
    private List<CommentBox> commentBox;

}
