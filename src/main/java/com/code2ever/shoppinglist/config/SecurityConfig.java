package com.code2ever.shoppinglist.config;

import org.springframework.context.annotation.Configuration;

//@EnableWebSecurity
@Configuration
public class SecurityConfig {

//    private UserDetailsService userDetailsService;
//
//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //These resources are excluded from security
//        web.ignoring().antMatchers("/webjars/**")
//                .antMatchers("/css/**")
//                .antMatchers("/js/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests().antMatchers("/login").permitAll()
//                .antMatchers("/view/signup").permitAll()
//                .anyRequest().authenticated();
//
//        http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("userId").passwordParameter("password").defaultSuccessUrl("/view/list/viewAllList",true);
//
//
//        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout");
//
//        http.csrf().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = passwordEncoder();
////        auth.inMemoryAuthentication().withUser("user").password(encoder.encode("user")).roles("GENERAL")
////                .and().withUser("admin").password(encoder.encode("admin")).roles("ADMIN");
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
//
//    }
}