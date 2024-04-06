package com.gisdev.dea.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserUtil {

    //Returns the role of logged-in user
    public static String getRoleOfLoggedInUser() {
        try {
            Collection<? extends GrantedAuthority> list = SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            return list.stream().findFirst().get().toString();
        } catch (NullPointerException exception) {
            return "";
        }
    }

    //Returns the username of logged-in user
    public static String getUsername() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public static CustomUserDetails getLoggedInUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
