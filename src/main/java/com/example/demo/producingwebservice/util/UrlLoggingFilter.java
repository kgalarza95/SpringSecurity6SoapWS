/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.producingwebservice.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author kgalarza
 */
@Component
public class UrlLoggingFilter extends
        OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtén la URL de la petición
        String url = request.getRequestURL().toString();
        System.out.println("URL de la petición: " + url);

        // Obtén el método HTTP
        String method = request.getMethod();

        // Obtén los encabezados de la petición
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        // Obtén los parámetros de la petición
        Map<String, String[]> parameters = request.getParameterMap();

        // Imprime la información de la petición
        System.out.println("URL de la petición: " + url);
        System.out.println("Método HTTP: " + method);
        System.out.println("Encabezados: " + headers);
        System.out.println("Parámetros: " + parameters);

        String requestBody = extractRequestBody(request);
        System.out.println("Cuerpo de la petición: " + requestBody);

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private String extractRequestBody(HttpServletRequest request) throws IOException {
        return "yes!";
//        try ( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
//            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
//        }
    }
}
