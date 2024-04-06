package com.gisdev.dea.dto.userDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotNull(message = "Password should not be empty!")
    private String password;

    @NotNull(message = "New password should not be empty!")
    private String resetPassword;
}
