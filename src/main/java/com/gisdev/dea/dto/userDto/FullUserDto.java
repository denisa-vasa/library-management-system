package com.gisdev.dea.dto.userDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullUserDto {

    @NotNull(message = "Name should not be empty!")
    private String name;

    @NotNull(message = "Surname should not be empty!")
    private String surname;

    @NotNull(message = "Username should not be empty!")
    private String username;

    @NotNull(message = "Email should not be empty!")
    private String email;

    @NotNull(message = "Phone number should not be empty!")
    private String phoneNumber;

    private Boolean active;
}
