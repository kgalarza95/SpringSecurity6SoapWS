
package com.example.demo.producingwebservice.modelo;

import java.io.Serializable;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author kgalarza
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String usuario;
    private String clave;

}
