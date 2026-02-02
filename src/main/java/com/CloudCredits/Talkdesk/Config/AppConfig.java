package com.CloudCredits.Talkdesk.Config;

import java.util.Arrays;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

            .csrf(csrf -> csrf.disable())

            .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            	
                @Override
                public @Nullable CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                    CorsConfiguration cfg = new CorsConfiguration();
                    cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(Arrays.asList("Authorization"));
                    cfg.setMaxAge(3600L);

                    return cfg;
                }
            }))

          
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)

            
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
