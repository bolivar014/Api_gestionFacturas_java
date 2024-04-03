package com.api_gestion_facturas.api_gestion_facturas.service;

import com.api_gestion_facturas.api_gestion_facturas.constantes.FacturasConstantes;
import com.api_gestion_facturas.api_gestion_facturas.dao.UserDAO;
import com.api_gestion_facturas.api_gestion_facturas.pojo.User;
import com.api_gestion_facturas.api_gestion_facturas.util.FacturaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public ResponseEntity<String> signUp(Map<String,String> requestMap){
        log.info("Registro interno de un usuario {}", requestMap);

        try{
            if(ValidateSignUpMap(requestMap)){
                User user = userDAO.findByEmail(requestMap.get("email"));

                if(Objects.isNull(user)){
                    userDAO.save(getUserFromMap(requestMap));

                    return FacturaUtils.getResponseEntity("Usuario registrado con éxito", HttpStatus.CREATED);
                } else {
                    return FacturaUtils.getResponseEntity("El usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
                }
            } else {
             return FacturaUtils.getResponseEntity(FacturasConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean ValidateSignUpMap(Map<String, String> requestMap){

        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroDeContacto") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }

        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumeroDeContacto(requestMap.get("numeroDeContacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus(requestMap.get("status"));
        user.setRole(requestMap.get("role"));

        return user;
    }
}
