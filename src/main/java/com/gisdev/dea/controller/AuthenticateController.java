package com.gisdev.dea.controller;

import com.gisdev.dea.dto.auth.AuthenticationRequest;
import com.gisdev.dea.dto.auth.AuthenticationResponse;
import com.gisdev.dea.dto.general.EntityIdDto;
import com.gisdev.dea.entity.Users;
import com.gisdev.dea.service.UsersService;
import com.gisdev.dea.util.constant.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstants.AuthController.BASE_PATH)
public class AuthenticateController {

    private final UsersService usersService;

    @PostMapping(value = RestConstants.AuthController.LOGIN)
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String jwt = usersService.createJwtToken(authenticationRequest);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(value = RestConstants.AuthController.SIGN_UP)
    public ResponseEntity<EntityIdDto> saveUser(@Validated @RequestBody Users users) {
        Users newUser = usersService.saveUser(users);
        return new ResponseEntity<>(EntityIdDto.of(newUser.getId()), HttpStatus.CREATED);
    }
}
