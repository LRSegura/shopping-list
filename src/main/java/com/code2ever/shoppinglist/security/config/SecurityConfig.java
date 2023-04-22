package com.code2ever.shoppinglist.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;


@Configuration
public class SecurityConfig {

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
//        http.httpBasic(withDefaults());
//        http.authorizeHttpRequests((authorize) ->
////                authorize.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//                        //.requestMatchers("/api/auth/**").permitAll()
//                        authorize.anyRequest().permitAll()
////                        .anyRequest().hasAnyAuthority("WRITE", "READ")
//        );
//        return http.build();
        http.httpBasic();
        http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class);
//        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class);
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());

//                        authorize.anyRequest().permitAll()
//                        .anyRequest().hasAnyAuthority("WRITE", "READ")

//        http.securityMatcher("/api/**")
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().hasRole("WRITE")
//                );

        return http.build();
    }
}