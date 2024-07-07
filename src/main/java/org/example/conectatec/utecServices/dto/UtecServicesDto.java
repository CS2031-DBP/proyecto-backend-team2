package org.example.conectatec.utecServices.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.conectatec.user.dto.UserDto;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.example.conectatec.utecServicesPost.dto.UtecServicesPostDto;
import java.util.ArrayList;
import java.util.List;

@Data
public class UtecServicesDto extends UserDto {
    @NotNull
    @Valid
    private List<UtecServicesPostDto> publications = new ArrayList<>();
    @NotNull
    @Valid
    private UtecServicesPost utecServicesPost;

}
