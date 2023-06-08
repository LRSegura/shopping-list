package com.code2ever.shoppinglist.api.security.authentication;

import com.code2ever.shoppinglist.api.security.user.JsonUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class WebServiceAuthentication {

    private final AuthenticationProviderService authenticationManager;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public WebServiceAuthentication(AuthenticationProviderService authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signing")
    public ResponseEntity<Void> authenticateUser(@RequestBody JsonUser jsonUser, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(jsonUser.userName(), jsonUser.password());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (AuthenticationException exception) {
            log.error("Access denied for user " + jsonUser.userName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/csrf")
    public ResponseEntity<Void> getCsrf(HttpServletRequest request, HttpServletResponse response) {
        Object o = request.getAttribute("_csrf");
        CsrfToken token = (CsrfToken) o;
        response.setHeader("X-CSRF-TOKEN", token.getToken());
        response.setHeader("Session", request.getSession().getId());
        return ResponseEntity.ok().build();
    }
}


