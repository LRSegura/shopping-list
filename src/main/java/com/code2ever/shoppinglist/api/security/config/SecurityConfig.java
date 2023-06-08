package com.code2ever.shoppinglist.api.security.config;

import com.code2ever.shoppinglist.api.security.CsrfTokenLogger;
import com.code2ever.shoppinglist.api.security.token.CustomCsrfTokenRepository;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@Slf4j
public class SecurityConfig {

    private final CustomCsrfTokenRepository tokenRepository;

    public SecurityConfig(CustomCsrfTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    //    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    //Only for test
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.csrf().csrfTokenRepository(tokenRepository);
        http.httpBasic(Customizer.withDefaults());
        http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class);
//        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class);
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/auth/**").hasRole("SECURITY")).authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
        http.logout(logout -> logout.logoutUrl("/api/auth/logout").addLogoutHandler(((request, response, authentication) -> {
            try {
                request.logout();
            } catch (ServletException e) {
                log.error(e.getMessage());
            }
        })));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:63342"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "X-CSRF-TOKEN", "Content-Type", "x-xsrf-token", "Session"));
        configuration.setExposedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    CsrfTokenRepository tokenRepository() {
        return new CustomCsrfTokenRepository();
    }
}