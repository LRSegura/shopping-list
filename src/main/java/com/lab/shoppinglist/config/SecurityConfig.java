package com.lab.shoppinglist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //These resources are excluded from security
        web.ignoring().antMatchers("/webjars/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/view/signup").permitAll()
                .anyRequest().authenticated();
        http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/login?error")
                .usernameParameter("userName").passwordParameter("password").defaultSuccessUrl("/view/list/viewAllList",true);

        http.csrf().disable();
    }
}