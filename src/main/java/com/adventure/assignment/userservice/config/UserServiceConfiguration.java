package com.adventure.assignment.userservice.config;
import com.adventure.assignment.userservice.exception.RestTemplateResponseErrorHandler;
import com.adventure.assignment.userservice.filter.JwtTokenValidationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class UserServiceConfiguration {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityContext().requireExplicitSave(false).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
                .addFilterBefore(jwtTokenValidationFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers(HttpMethod.POST,"/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAnyRole("ADMIN", "USER");
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Bean
    public JwtTokenValidationFilter jwtTokenValidationFilter() {
        return new JwtTokenValidationFilter();
    }

}
