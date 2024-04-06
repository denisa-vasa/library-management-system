package com.gisdev.dea.controller;

import com.gisdev.dea.dto.userDto.FullUserDto;
import com.gisdev.dea.dto.userDto.UserDto;
import com.gisdev.dea.entity.Users;
import com.gisdev.dea.service.UsersService;
import com.gisdev.dea.util.constant.RestConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(RestConstants.UsersController.BASE_PATH)
public class UsersController {

    private UsersService usersService;

    @PutMapping(RestConstants.UsersController.RESET_PASSWORD)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<String> resetPassword(@PathVariable Long id,
                                                @RequestBody UserDto userDto) {
        usersService.resetPassword(id, userDto);
        return ResponseEntity.ok("Password was updated successfully!");
    }

    @PutMapping(RestConstants.UsersController.UPDATE_USER)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody FullUserDto fullUserDto) {
        usersService.updateUser(id, fullUserDto);
        return ResponseEntity.ok("User has updated successfully!!!");
    }

    @PutMapping(RestConstants.UsersController.ACTIVITY)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<Long> updateActivity(@PathVariable Long id,
                                               @RequestParam Boolean active) {
        usersService.updateActivity(id, active);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(RestConstants.UsersController.ACTUAL_USER)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<Users> getCurrentUser() {
        Users users = usersService.getCurrentUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
