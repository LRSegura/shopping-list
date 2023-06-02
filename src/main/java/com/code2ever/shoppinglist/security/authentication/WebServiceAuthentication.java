package com.code2ever.shoppinglist.security.authentication;

import com.code2ever.shoppinglist.security.user.JsonUser;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class WebServiceAuthentication {

    private final AuthenticationProviderService authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public WebServiceAuthentication(AuthenticationProviderService authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signing")
    public ResponseEntity<Void> authenticateUser(@RequestBody JsonUser jsonUser, HttpServletRequest request,
                                              HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                jsonUser.userName(), jsonUser.password());
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(token);
        } catch (AuthenticationException exception){
            log.error("Access denied for user "+ jsonUser.userName());
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

//        Cookie cookie = new Cookie("TestCookie", "valuecookie");
//        cookie.setSecure(false);
//        cookie.setPath("/api/auth");
//        cookie.setMaxAge(50000);
//        cookie.setHttpOnly(false);
//        response.addCookie(cookie);
//    }

//        @GetMapping("/csrf")
//    public ResponseEntity<String> getCsrf(ServletRequest request, ServletResponse response) {
//        Object o = request.getAttribute("_csrf");
//        CsrfToken token = (CsrfToken) o;
//        return new ResponseEntity<>(token.getToken(), HttpStatus.OK);
//    }
//    XSRF-TOKEN=7c39595f-6a8a-441e-a31c-71dad31b83a4; Path=/

//    record Token(String token){
//
//    }
}


