package com.webapp.lab4.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin
@RestController
public class MainController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        if (ex instanceof HttpClientErrorException) {
            HttpClientErrorException hEx = (HttpClientErrorException) ex;
            return new ResponseEntity<>(hEx.getStatusText(), hEx.getStatusCode());
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED );
    }
}
