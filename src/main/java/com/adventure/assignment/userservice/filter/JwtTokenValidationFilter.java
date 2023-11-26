package com.adventure.assignment.userservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class JwtTokenValidationFilter extends OncePerRequestFilter {
    public static final String JWT_HEADER = "Authorization";
    @Autowired
    private RestTemplate restTemplate;

    @Value("${authentication.service.url}")
    private String authenticationURL;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            log.info("Validating JWT Token");
            String jwtToken = request.getHeader(JWT_HEADER);
            if (null != jwtToken) {
                HttpHeaders headers = new HttpHeaders();
                headers.set(JWT_HEADER, jwtToken);
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(authenticationURL, requestEntity, String.class);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, AuthorityUtils.createAuthorityList(responseEntity.getBody()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Token Validated Successfully");
                } else {
                    log.error("Error while validating token " + responseEntity.getStatusCode());
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e){
            log.error("Error while validating JWT token : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/h2-console");
    }
}
