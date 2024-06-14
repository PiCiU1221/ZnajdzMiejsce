package com.zut.znajdzmiejsce.security.config;

import com.zut.znajdzmiejsce.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/authenticate/**").permitAll()
                        .requestMatchers("/api/register/**").permitAll()

                        // Reservations
                        .requestMatchers("/api/reservations/**").hasAuthority("USER")

                        // Parking lots
                        .requestMatchers(HttpMethod.GET, "/api/parking-lots/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/parking-lots/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/parking-lots/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/parking-lots/**").hasRole("ADMINISTRATOR")

                        // Parking spots
                        .requestMatchers(HttpMethod.GET, "/api/parking-lots/**/parking-spots/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/parking-lots/**/parking-spots/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/parking-lots/**/parking-spots/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/parking-lots/**/parking-spots/**").hasRole("ADMINISTRATOR")

                        // Parking spot statuses
                        // temporary
                        .requestMatchers(HttpMethod.GET, "/api/parking-lots/**/parking-spots/**/statuses/**").permitAll()

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}