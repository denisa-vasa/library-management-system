package com.gisdev.dea.dto.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityIdDto {

    private Long id;

    public static EntityIdDto of(Long id) {
        return new EntityIdDto(id);
    }
}
