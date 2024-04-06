package com.gisdev.dea.security;

import com.gisdev.dea.dataType.Role;
import org.springframework.stereotype.Component;

@Component("Role")
public final class RoleName {
    public static final String ADMIN_NAME = Role.ADMIN.getName();
    public static final String USER_NAME = Role.USER.getName();
}
