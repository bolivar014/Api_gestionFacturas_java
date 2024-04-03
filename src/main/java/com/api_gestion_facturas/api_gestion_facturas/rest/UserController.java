package com.api_gestion_facturas.api_gestion_facturas.rest;

import com.api_gestion_facturas.api_gestion_facturas.constantes.FacturasConstantes;
import com.api_gestion_facturas.api_gestion_facturas.service.UserService;
import com.api_gestion_facturas.api_gestion_facturas.util.FacturaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> RegistrarUsuario(@RequestBody(required = true)Map<String,String> requestMap){
        try
        {
            userService.signUp(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
