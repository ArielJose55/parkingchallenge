package co.com.ceiba.parkingchallenge.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.exceptions.NotSaveModelException;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.models.ExceptionResponse;

@ControllerAdvice
public class HandleExceptionController {

	@ExceptionHandler(NotFountModelException.class)
	public <T extends NotFountModelException> ResponseEntity<ExceptionResponse> handleException(T ex,  WebRequest request){
		ex.printStackTrace();
		ExceptionResponse detalls = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(detalls, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(NotSaveModelException.class)
	public <T extends NotSaveModelException> ResponseEntity<ExceptionResponse> handleException(T ex,  WebRequest request){
		ex.printStackTrace();
		ExceptionResponse detalls = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(detalls, HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(ViolatedConstraintException.class)
	public <T extends ViolatedConstraintException> ResponseEntity<ExceptionResponse> handleException(T ex,  WebRequest request){
		ex.printStackTrace();
		ExceptionResponse detalls = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(detalls, HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ex.printStackTrace();
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(true));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
