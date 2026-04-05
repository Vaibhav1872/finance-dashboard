package com.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth

               
                .requestMatchers("/auth/**").permitAll()

             
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                
                .requestMatchers("/dashboard/**")
                    .hasAnyAuthority("ROLE_ANALYST", "ROLE_ADMIN")

               
                .requestMatchers(HttpMethod.GET, "/records/**")
                    .hasAnyAuthority("ROLE_VIEWER", "ROLE_ANALYST", "ROLE_ADMIN")

               
                .requestMatchers(HttpMethod.POST, "/records/**")
                    .hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.PUT, "/records/**")
                    .hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/records/**")
                    .hasAuthority("ROLE_ADMIN")

                .anyRequest().authenticated()
            )

            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}