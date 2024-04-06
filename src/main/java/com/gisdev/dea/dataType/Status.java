package com.gisdev.dea.dataType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    public String name;
}
