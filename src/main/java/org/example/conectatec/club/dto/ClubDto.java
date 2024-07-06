package org.example.conectatec.club.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
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
    private ClubFeedDto clubFeed;

    private List<ClubFeedDto> clubFeeds;
}
