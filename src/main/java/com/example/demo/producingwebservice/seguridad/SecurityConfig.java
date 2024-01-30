package com.example.demo.producingwebservice.seguridad;

import com.example.demo.producingwebservice.util.CustomAuthorizationFilter;
import com.example.demo.producingwebservice.util.UrlLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author kgalarza
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JWTRequestFiltro jwtRequestFiltro;
    @Autowired
    private UrlLoggingFilter urlLoggingFilter;
    @Autowired
    private CustomAuthorizationFilter customAuthorizationFilter;

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/**").permitAll()
                //.requestMatchers("/ws/**").authenticated()
                .anyRequest().authenticated()
                )
                .cors(withDefaults())
                //.addFilterBefore(urlLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtRequestFiltro, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

//    @Bean
//    SecurityFilterChain web(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // (2)
//                .authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("/publico/**").permitAll()
//                .requestMatchers("/privado/v1/admin/**").hasRole("ADMIN")
//                .requestMatchers("/privado/**").authenticated()
//                .anyRequest().authenticated()
//                )
//                .cors(withDefaults())
//                .addFilterBefore(jwtRequestFiltro, UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement((session) -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();
//    }
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().anyRequest().permitAll();
//
//        return http.build();
//    }
}
