package org.example.conectatec.club.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.clubPost.dto.ClubPostDto;
import org.example.conectatec.user.dto.UserDto;

import java.util.List;

@Data
public class ClubDto extends UserDto {
    @NotNull
    private Long id;
    @NotNull
    @Valid
    private CareerDto career;
    @NotNull
    @Valid
    private ClubPostDto clubPost;

    private List<ClubPostDto> clubPosts;
}
