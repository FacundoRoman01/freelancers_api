package com.facundoroman.freelancers_api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions( 
	        MethodArgumentNotValidException ex, WebRequest request) {

	    Map<String, String> errors = new HashMap<>(); 
	    ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
	    
	    Map<String, Object> body = new HashMap<>(); // 
	    body.put("timestamp", LocalDateTime.now());
	    body.put("status", HttpStatus.BAD_REQUEST.value());
	    body.put("error", "Bad Request"); 
	    body.put("message", "Error de validación para el cuerpo de la solicitud."); 
	    body.put("details", errors); 
	    body.put("path", request.getDescription(false).replace("uri=", "")); 

	   
	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); 
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class) 
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage()); 
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
	
	//  EXCEPTION HANDLER PARA ResourceAlreadyExistsException!
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value()); 
        body.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
	
	
	
	
}
