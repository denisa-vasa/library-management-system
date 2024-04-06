package com.gisdev.dea.dataType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("Admin"),
    USER("User");

    public String name;
}
