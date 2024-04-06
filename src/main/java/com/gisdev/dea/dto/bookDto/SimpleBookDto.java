package com.gisdev.dea.dto.bookDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SimpleBookDto {

    @NotNull(message = "Id should not be empty!")
    private Long id;

    @NotNull(message = "Quantity should not be empty!")
    private Integer quantity;

}
