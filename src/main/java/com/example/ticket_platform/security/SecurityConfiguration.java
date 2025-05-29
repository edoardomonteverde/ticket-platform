package com.example.ticket_platform.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/* @Configuration
public class SecurityConfiguration {

    private final DatabaseUserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/tickets/create").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().permitAll()
                .and().formLogin()
                .and().logout()
                .and().csrf().disable();

        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService(null);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

@Bean
    public DaoAuthenticationProvider authenticationProvider(DatabaseUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        return http
            .getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authProvider)
            .build();
    }  
} */

@Configuration
public class SecurityConfiguration {

    private final DatabaseUserDetailsService userDetailsService;

    public SecurityConfiguration(DatabaseUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
    .authorizeHttpRequests()
    .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
    .requestMatchers("/create").hasAnyAuthority("USER", "ADMIN")
    .anyRequest().authenticated()
    .and().formLogin()
        .loginPage("/login")
        .permitAll()
    .and().logout()
        .permitAll()
    .and().csrf().disable();


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .authenticationProvider(authenticationProvider())
                   .build();
    }
} 

