package com.gisdev.dea.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library")
public class Library extends AbstractEntity{

    @NotEmpty(message = "Name should not be empty!")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Location should not be empty!")
    @Column(name = "location")
    private String location;

    @Column(name = "num_products")
    private Integer numProducts;
}
