package com.mehmedmaljoki.readit.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .authorizeHttpRequests(
        authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/api/books").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/books/reviews").permitAll()
            .requestMatchers("/api/**").authenticated() // everything else beside the two needs authentication
      )
      .sessionManagement(sessionManagement ->
          sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .cors(Customizer.withDefaults())
      .csrf(AbstractHttpConfigurer::disable)
      .oauth2ResourceServer(
        oauth2 ->
          oauth2.jwt(
            jwt -> jwt.jwtAuthenticationConverter(new CustomAuthenticationConverter())));

    return httpSecurity.build();
  }
}
