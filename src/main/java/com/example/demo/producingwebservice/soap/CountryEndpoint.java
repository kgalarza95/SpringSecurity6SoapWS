package com.example.demo.producingwebservice.soap;

import com.example.demo.producingwebservice.endpoint.UnauthorizedException;
import com.example.demo.producingwebservice.seguridad.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import io.spring.guides.gs_producing_web_service.AuthenticateRequest;
import io.spring.guides.gs_producing_web_service.AuthenticateResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @fecha 29/01/2024
 * @author kgalarza
 */
@Endpoint
public class CountryEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ROLE_USER')")
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AuthenticateRequest")
    @ResponsePayload
    public AuthenticateResponse authenticate(@RequestPayload AuthenticateRequest authenticateRequest) {
        System.out.println("LLega al endpoint");
        // Lógica para la operación SOAP equivalente a /publico/v1/authenticate
        AuthenticateResponse resp = new AuthenticateResponse();
        try {
            // Validar usuario y contraseña (ajustar según tus necesidades)
            if (!authenticateRequest.getClave().equals("1234567890")) {
                throw new UnauthorizedException("Credenciales incorrectas");
            }

            // Cargar detalles del usuario
            final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                    authenticateRequest.getUsuario());

            // Generar token JWT
            final String jwt = jwtUtilService.generateToken(userDetails);
            resp.setToken(jwt);
            return resp;
        } catch (Exception e) {
            // Manejar errores y responder según sea necesario
            throw new RuntimeException("Error al autenticar", e);
        }
    }

//================================================================================
    @Autowired
    private UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TestRequest")
    @ResponsePayload
    public String test() {
        return "Hola Mundo desde el servicio SOAP";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaludoRequest")
    @ResponsePayload
    public String saludar(String nombre) {
        return "Hola, " + nombre + "!";
    }

}
