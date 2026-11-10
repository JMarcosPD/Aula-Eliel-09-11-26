
package com.bn.funcionario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()   //esse caminho ainda não existe, tem que criar
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()   //esse caminho ainda não existe, tem que criar
                        .requestMatchers(HttpMethod.POST, "/funcionarios").hasRole("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/funcionarios").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/funcionarios").hasRole("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/funcionarios").hasRole("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        securityFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
