package org.example.conectatec.club.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
import org.example.conectatec.user.dto.UserDto;
@Data
public class ClubDto extends UserDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 50)
    private String carrera;

    @NotNull
    @Valid
    private CareerDto career;
    @NotNull
    @Valid
    private ClubFeedDto clubFeed;
}
