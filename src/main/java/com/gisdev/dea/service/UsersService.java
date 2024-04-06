package com.gisdev.dea.service;

import com.gisdev.dea.dto.userDto.FullUserDto;
import com.gisdev.dea.dto.auth.AuthenticationRequest;
import com.gisdev.dea.dto.userDto.UserDto;
import com.gisdev.dea.entity.Users;
import com.gisdev.dea.exception.BadRequestException;
import com.gisdev.dea.exception.ConflictException;
import com.gisdev.dea.exception.NotFoundException;
import com.gisdev.dea.exception.UnauthorizedException;
import com.gisdev.dea.repository.UserRepository;
import com.gisdev.dea.security.CustomUserDetails;
import com.gisdev.dea.security.JwtUtil;
import com.gisdev.dea.security.UserUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.gisdev.dea.util.general.DisplayHelper.retrieveNotNull;

@Slf4j
@Service
public class UsersService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        JwtUtil jwtTokenUtil, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + user + " was not found!");
        } else {
            Users foundUser = user.get();
            CustomUserDetails.UserBuilder builder = CustomUserDetails
                    .username(username)
                    .password(foundUser.getPassword())
                    .authorities(Set.of(new SimpleGrantedAuthority(foundUser.getRole().getName())))
                    .disabled(!foundUser.getActive())
                    .id(foundUser.getId());

            return builder.build();
        }
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users save(Users users) {
        return userRepository.save(users);
    }

    public String createJwtToken(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                            authenticationRequest.getPassword())
            );

        } catch (DisabledException ex) {
            throw new UnauthorizedException("This username is not active!");

        } catch (AuthenticationException e) {
            throw new NotFoundException("Username or password is not correct!");
        }

        UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());

        return jwtTokenUtil.generateToken(userDetails);
    }

    public Users saveUser(Users requestedUsers) {

        if (findByUsername(requestedUsers.getUsername()).isPresent()) {
            throw new ConflictException("User with this username is already in this system!");
        }

        Users newUser = new Users();
        newUser.setName(requestedUsers.getName());
        newUser.setSurname(requestedUsers.getSurname());
        newUser.setUsername(requestedUsers.getUsername());
        newUser.setEmail(requestedUsers.getEmail());
        newUser.setBirthday(requestedUsers.getBirthday());
        newUser.setPassword(passwordEncoder.encode(requestedUsers.getPassword()));
        newUser.setRole(requestedUsers.getRole());
        newUser.setPhoneNumber(requestedUsers.getPhoneNumber());
        newUser.setActive(retrieveNotNull(requestedUsers.getActive()));
        newUser = save(newUser);

        return newUser;
    }

    public Users getCurrentUser() {
        return findByUsername(UserUtil.getUsername())
                .orElseThrow(() -> new NotFoundException(String.format("User with username: %s was not found!",
                                                                        UserUtil.getUsername())));
    }

    public Users findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " was not found!"));
    }

    public void validatePassword(UserDto userDto) {
        if (userDto.getPassword().length() < 7)
            throw new BadRequestException("Password should not be less than 7 characters!!!");
        if (!userDto.getPassword().matches(".*[A-Z].*"))
            throw new BadRequestException("Password should contain at least one uppercase letter!");
        if (!userDto.getPassword().matches(".*\\d.*"))
            throw new BadRequestException("Password should contain at least one number!");
        if (!userDto.getPassword().matches(".*[!@#$%^&*(){}\\[\\]].*"))
            throw new BadRequestException("Password should contain at least one special character!");
    }

    public void resetPassword(Long id, UserDto userDto) {
        Users users = findById(id);
        validatePassword(userDto);
        if (!userDto.getPassword().equals(userDto.getResetPassword()))
            throw new BadRequestException("Password is not the same!!!");
        else {
            users.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(users);
        }
    }

    public void updateUser(Long id, FullUserDto fullUserDto) {
        Users users = findById(id);

        users.setName(fullUserDto.getName());
        users.setSurname(fullUserDto.getSurname());
        users.setUsername(fullUserDto.getUsername());
        users.setEmail(fullUserDto.getEmail());
        users.setPhoneNumber(fullUserDto.getPhoneNumber());
        userRepository.save(users);
    }

    public void updateActivity(Long id, Boolean active) {
        Users users = findById(id);

        users.setActive(!active);
        userRepository.save(users);
    }
}
