package com.gisdev.dea.entity;

import com.gisdev.dea.dataType.Sector;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book extends AbstractEntity{

    @NotEmpty(message = "Title should not be empty!")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author should not be empty!")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Genre should not be empty!")
    @Column(name = "genre")
    private String genre;

    @NotNull(message = "Price should not be empty!")
    @Column(name = "price")
    private Double price;

    @NotNull(message = "Sector should not be empty!")
    @Column(name = "sector")
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @Column(name = "stock")
    private Integer stock;
}
