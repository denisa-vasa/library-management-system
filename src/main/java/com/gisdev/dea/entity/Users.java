package com.gisdev.dea.entity;

import com.gisdev.dea.dataType.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users extends AbstractEntity {

    @NotEmpty(message = "Name should not be empty!")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Surname should not be empty!")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Username should not be empty!")
    @Size(message = "Username should have 5 to 16 characters!", min = 5, max = 16)
    @Column(name = "username")
    private String username;

    @Email(message = "Email is not in the right format!")
    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @NotEmpty(message = "Password should not be empty!")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Role should not be empty!")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Orders> ordersList;

    public Boolean getActive() {
        return Boolean.TRUE.equals(active);
    }
}
