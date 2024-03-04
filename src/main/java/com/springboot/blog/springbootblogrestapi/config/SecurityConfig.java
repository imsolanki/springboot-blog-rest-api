package com.springboot.blog.springbootblogrestapi.config;

import com.springboot.blog.springbootblogrestapi.security.JWTAuthenticationEntryPoint;
import com.springboot.blog.springbootblogrestapi.security.JWTAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name="Bearer Authentication",
        type= SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JWTAuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,JWTAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
        this.authenticationFilter=authenticationFilter;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize)->
                //authorize.anyRequest().authenticated()
                    authorize.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/v2/**").permitAll()
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated()
            ).exceptionHandling(exception ->exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            ).sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails lalit = User.builder()
//                .username("lalit")
//                .password(passwordEncoder().encode("lalit"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(lalit,admin);
//    }
}

