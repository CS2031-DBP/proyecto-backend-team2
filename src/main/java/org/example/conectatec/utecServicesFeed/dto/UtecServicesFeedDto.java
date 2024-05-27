package org.example.conectatec.utecServicesFeed.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.commentBox.dto.CommentBoxDto;

import java.util.List;

@Data
public class UtecServicesFeedDto {
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
    private List<CommentBoxDto> commentBoxUTEC;
}
