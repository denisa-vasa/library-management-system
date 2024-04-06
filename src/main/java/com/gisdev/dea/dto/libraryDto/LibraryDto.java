package com.gisdev.dea.dto.libraryDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LibraryDto {

    @NotNull(message = "Number of products shoud not be empty!")
    private Integer numProducts;
}
