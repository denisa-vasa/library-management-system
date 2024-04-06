package com.gisdev.dea.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisdev.dea.dto.general.ResponseError;
import com.gisdev.dea.service.UsersService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.gisdev.dea.util.constant.RestConstants.AUTHORIZATION_HEADER;
import static com.gisdev.dea.util.constant.RestConstants.BEARER_TOKEN;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UsersService usersService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UsersService usersService, JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_TOKEN)) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }
        } catch (ExpiredJwtException ex) {
            buildResponse(response, request, "Token expired!", HttpServletResponse.SC_FORBIDDEN);
            return;
        } catch (SignatureException ex) {
            buildResponse(response, request, "Token not valid!", HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.usersService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private void buildResponse(HttpServletResponse response, HttpServletRequest request,
                               String message, int statusCode) throws IOException {
        response.resetBuffer();
        response.setStatus(statusCode);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(new ObjectMapper().writeValueAsString(new ResponseError(message)).getBytes());
        response.flushBuffer();
    }

}