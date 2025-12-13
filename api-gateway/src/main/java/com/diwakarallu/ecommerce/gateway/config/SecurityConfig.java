package com.diwakarallu.ecommerce.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.web.server.SecurityFilterChain;
// Remove Cors related imports
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String[] freeResourceUrls = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-resources/**",
            "/aggregate/**",
            "/eureka/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                freeResourceUrls)
                        .permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}

// package com.diwakarallu.ecommerce.gateway.config;

// import java.util.Arrays;
// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// @Configuration
// public class SecurityConfig {

// private final String[] freeResourceUrls = {
// "/swagger-ui.html",
// "/swagger-ui/**",
// "/api-docs/**",
// "/swagger-resources/**",
// "/aggregate/**"
// };

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
// throws Exception {
// return httpSecurity
// .authorizeHttpRequests(authorize -> authorize
// .requestMatchers(freeResourceUrls).permitAll()
// .anyRequest().authenticated())
// .cors(corsConfigurer ->
// corsConfigurer.configurationSource(corsConfigurationSource()))
// .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
// .build();
// }

// @Bean
// CorsConfigurationSource corsConfigurationSource() {
// CorsConfiguration configuration = new CorsConfiguration();
// configuration.setAllowedOrigins(List.of("*"));
// configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
// configuration.setAllowedHeaders(List.of("*"));
// configuration.setAllowCredentials(true);
// UrlBasedCorsConfigurationSource source = new
// UrlBasedCorsConfigurationSource();
// source.registerCorsConfiguration("/**", configuration);
// return source;
// }
// }
