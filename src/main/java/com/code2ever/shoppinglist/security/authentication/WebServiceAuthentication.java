package com.code2ever.shoppinglist.security.authentication;

import com.code2ever.shoppinglist.security.user.JsonUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class WebServiceAuthentication {

    private final AuthenticationProviderService authenticationManager;

    public WebServiceAuthentication(AuthenticationProviderService authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signing")
    public ResponseEntity<String> authenticateUser(@RequestBody JsonUser jsonUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jsonUser.userName(), jsonUser.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @GetMapping("/csrf")
    public ResponseEntity<String> getCsrf() {
        return new ResponseEntity<>("get successfully", HttpStatus.OK);
    }

    @PostMapping("/csrf")
    public ResponseEntity<String> postCsrf() {
        return new ResponseEntity<>("post successfully", HttpStatus.OK);
    }
}
