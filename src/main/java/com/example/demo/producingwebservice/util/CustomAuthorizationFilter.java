package com.example.demo.producingwebservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author kgalarza
 */
@Component
public class CustomAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Aquí puedes realizar acciones después de una autenticación exitosa si es necesario
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // Aquí puedes realizar acciones después de una autenticación fallida si es necesario
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // Extraer el cuerpo de la solicitud
        String requestBody;
        try {
            requestBody = extractRequestBody(request);

            // Verificar si el cuerpo contiene "AuthenticateRequest"
            if (requestBody.contains("AuthenticateRequest")) {
                System.out.println("la petición viene de AuthenticateRequest");
            } else if (requestBody.contains("getCountryRequest")) {
                System.out.println("la petición viene de getCountryRequest");
            }
        } catch (IOException ex) {
            Logger.getLogger(CustomAuthorizationFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Continuar con el proceso de autenticación predeterminado
        return super.attemptAuthentication(request, response);
    }

    private String extractRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = request.getReader();
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }
}
