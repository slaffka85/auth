package com.egartech.auth;

import com.egartech.security.SecurityService;
import com.egartech.security.User;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SecurityService securityService;

    public LoginSuccessHandler(@NonNull SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException {
        User user = securityService.createUser(authentication);
        // ...
    }
}
