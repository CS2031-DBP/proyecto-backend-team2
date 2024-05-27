package org.example.conectatec.commentBox.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.clubPublications.dto.ClubPublicationsDto;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.studentPublications.dto.StudentPublicationsDto;
import org.example.conectatec.utecServicesFeed.dto.UtecServicesFeedDto;

import java.util.List;

@Data
public class CommentBoxDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 500)
    private String content;

    @NotNull
    @Valid
    private StudentPublicationsDto publication;

    @NotNull
    @Valid
    private StudentDto student;

    @NotNull
    @Valid
    private ClubPublicationsDto clubPublication;

    @NotNull
    @Valid
    private UtecServicesFeedDto utecPublication;
}
