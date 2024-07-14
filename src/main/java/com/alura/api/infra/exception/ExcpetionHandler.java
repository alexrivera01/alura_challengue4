package com.alura.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcpetionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity manejadorDeError400(MethodArgumentNotValidException e){
        var datosErrorValidacion = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(datosErrorValidacion);
    }

    private record DatosErrorValidacion(
            String campo,
            String errores
    ){
        public DatosErrorValidacion(FieldError fieldError){
            this(fieldError.getField(),fieldError.getDefaultMessage());
        }
    }

}
