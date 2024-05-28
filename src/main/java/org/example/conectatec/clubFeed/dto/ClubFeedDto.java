package org.example.conectatec.clubFeed.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.commentBox.dto.CommentBoxDto;

import java.util.List;

@Data
public class ClubFeedDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 500)
    private String caption;

    @NotNull
    private String media; // Assuming media is stored as a URL or simple string path

    @NotNull
    @Valid
    private CareerDto career;

    @NotNull
    @Valid
    private List<CommentBoxDto> comments;
}
