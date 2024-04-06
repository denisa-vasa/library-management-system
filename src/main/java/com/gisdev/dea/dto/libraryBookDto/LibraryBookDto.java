package com.gisdev.dea.dto.libraryBookDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LibraryBookDto {

    @NotNull(message = "Book id should not be empty!")
    private Long bookId;

    @NotNull(message = "Library id should not be empty!")
    private Long libraryId;
}
