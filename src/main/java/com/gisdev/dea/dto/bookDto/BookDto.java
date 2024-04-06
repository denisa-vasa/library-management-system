package com.gisdev.dea.dto.bookDto;

import com.gisdev.dea.dataType.Sector;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Long id;

    @NotNull(message = "Title should not be empty!")
    private String title;

    @NotNull(message = "Author should not be empty!")
    private String author;

    @NotNull(message = "Genre should not be empty!")
    private String genre;

    @NotNull(message = "Price should not be empty!")
    private Double price;

    @NotNull(message = "Sector should not be empty!")
    private Sector sector;

    @Positive(message = "Stock should not be a negative number!")
    private Long stock;
}
