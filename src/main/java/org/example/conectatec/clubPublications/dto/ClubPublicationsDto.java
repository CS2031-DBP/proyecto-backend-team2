package org.example.conectatec.clubPublications.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.commentBox.dto.CommentBoxDto;

import javax.print.attribute.standard.Media;
import java.util.List;

@Data
public class ClubPublicationsDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 500)
    private String caption;

    @NotNull
    private Media media;

    @NotNull
    @Valid
    private CareerDto career;

    @NotNull
    @Valid
    private List<CommentBoxDto> comments;
}
