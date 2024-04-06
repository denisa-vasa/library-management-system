package com.gisdev.dea.dataType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sector {
    FOREIGN_BOOK("Foreign book"),
    ALBANIAN_BOOK("Albanian book"),
    MAGAZINE("Magazine"),
    ACCESSORIES_AND_GIFTS("Accessories and gifts");

    public String name;
}
