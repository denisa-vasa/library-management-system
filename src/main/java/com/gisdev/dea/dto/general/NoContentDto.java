package com.gisdev.dea.dto.general;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class NoContentDto<T> extends ResponseEntity<T> {

    private T message;

    public NoContentDto(T message) {
        super(message, HttpStatus.NO_CONTENT);
    }
}
