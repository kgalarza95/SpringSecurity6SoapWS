package com.example.demo.producingwebservice.endpoint;

import com.example.demo.producingwebservice.modelo.RequestUsuario;
import com.example.demo.producingwebservice.seguridad.JwtUtilService;
import com.example.demo.producingwebservice.seguridad.TokenGenerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author kgalarza
 */
@Endpoint
public class AutenticacionEndPoint {

    private static final String NAMESPACE_URI = "http://example.com";

    @Autowired
    private UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TestRequest")
    @ResponsePayload
    public String test() {
        return "Hola Mundo desde el servicio SOAP";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AuthenticateRequest")
    @ResponsePayload
    public String authenticate(RequestUsuario authenticateRequest) {
        // Lógica para la operación SOAP equivalente a /publico/v1/authenticate
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

            return jwt;
        } catch (Exception e) {
            // Manejar errores y responder según sea necesario
            throw new RuntimeException("Error al autenticar", e);
        }
    }
}
