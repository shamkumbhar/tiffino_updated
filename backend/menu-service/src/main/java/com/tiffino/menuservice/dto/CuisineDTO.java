package com.tiffino.menuservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuisineDTO {
    private Long id;

    @NotBlank(message = "Cuisine name is required")
    private String name;

    private String imageUrl;

    @NotNull(message = "State ID is required")
    private Long stateId;
}
