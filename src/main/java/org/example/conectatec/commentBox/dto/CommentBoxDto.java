package org.example.conectatec.commentBox.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.example.conectatec.utecServicesFeed.dto.UtecServicesFeedDto;

@Data
public class CommentBoxDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 500)
    private String content;

    @Valid
    private StudentFeedDto publication;

    @NotNull
    @Valid
    private StudentDto student;

    @Valid
    private ClubFeedDto clubPublication;

    @Valid
    private UtecServicesFeedDto utecPublication;
}
