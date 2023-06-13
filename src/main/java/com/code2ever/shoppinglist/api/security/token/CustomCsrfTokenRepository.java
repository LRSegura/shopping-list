package com.code2ever.shoppinglist.api.security.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final Map<String, CsrfToken> mapToken = new HashMap<>();

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        mapToken.put(session.getId(),token);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String firstSession = request.getHeader("Session");
        return mapToken.get(firstSession);
    }
}
