package com.baz.divisas.general.exception;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;


@RestControllerAdvice
public class GlobalExceptionHandlerController {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseApi handleAccessDeniedException(AccessDeniedException e) {
    return new ResponseApi(EstatusResponse.NO_PERMITIDO, 
			   			   Arrays.asList("No se cuentan con permisos suficientes."));   
  }  
	  
  // Login - Credenciales incorrectas
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseApi handleBadCredentialsException(BadCredentialsException e) {    
    return new ResponseApi(EstatusResponse.NO_AUTORIZADO, 
    					   Arrays.asList("Credenciales incorrectas, favor de validar su información."));   
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public ResponseApi handleBindException(BindException e) {

	  final List<String> errors = new ArrayList<>();
	  
	  for (final FieldError error : e.getBindingResult().getFieldErrors()) {
		  errors.add(error.getField() + ": " + error.getDefaultMessage());
	  }
	  for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
		  errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	  }

	  return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, errors);      
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseApi handleConstraintViolationException(ConstraintViolationException e) {
	  
	  final List<String> errors = new ArrayList<>();
		
	  for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
			errors.add(cv.getPropertyPath()+":"+cv.getMessage());
	  }
		
	  return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(errors));      
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseApi handleMissingRequestHeaderException(MissingRequestHeaderException e) {    
    return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
			   			   Arrays.asList("Header requerido.")); 
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseApi handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
	  return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList("Peticion incorrecta."));      
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(GeneralSecurityException.class)
  public ResponseApi handleGeneralSecurityException(GeneralSecurityException e) {
	  
	  LogApp.excepcion( (Exception) e, "Error al encriptar o desencriptar la información");
	  
	  return new ResponseApi(EstatusResponse.ERROR_INTERNO, 
			  				 Arrays.asList("No fue posible encriptar o desencriptar la información"));      
  }
  
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseApi handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
	  
	  LogApp.excepcion( (Exception) e, "Metodo HTTP no permitido");
	  
	  return new ResponseApi(EstatusResponse.ERROR_INTERNO, 
			  				 Arrays.asList("Metodo HTTP no permitido"));      
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  public ResponseApi handleException(Throwable e) {	 

	  LogApp.excepcion( (Exception) e, "Error interno");

	  return new ResponseApi(EstatusResponse.ERROR_INTERNO,
			  Arrays.asList("Problemas al procesar su solicitud favor de contactar a su administrador."));   

  }

}
