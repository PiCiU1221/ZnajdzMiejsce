package com.zut.znajdzmiejsce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocalizationDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
