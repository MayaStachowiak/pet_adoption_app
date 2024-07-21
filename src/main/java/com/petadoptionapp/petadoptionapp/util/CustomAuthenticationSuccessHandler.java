package com.petadoptionapp.petadoptionapp.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("Rola: " + authorities);

        authorities.forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                try {
                    response.sendRedirect("/admin/home");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                try {
                    response.sendRedirect("/user/home");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalStateException();
            }
        });
    }
}
