package com.soundify.exception_handler;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.dto.ApiResponse;

//catch methods for the try blocks 
@RestControllerAdvice // To tell SC , following class is centralized exc handler ,
//it will work as  common advice to all rest controllers
public class GlobalExceptionHandler {
	// exceprion handling(catch block) logic : repeatative logic
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		System.out.println("method arg invalid " + e);
		// API : List<FieldError> getFieldErrors()
		//convert list of field errs -->Map<Field name , Error mesg>
		Map<String, String> errMap=new HashMap<>();
		for(FieldError field : e.getFieldErrors()) {
			System.out.println("in global exception handler field=> "+field.getField()+" message = "+field.getDefaultMessage());
			errMap.put(field.getField(), field.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMap);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
		System.out.println("method arg invalid " + e);
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(e.getMessage()));
	}
	
	//As a project tip: handle important exceptions separately
	//others can be handled by a catch-all type of a block
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(RuntimeException e){
		System.out.println("in catch-all");
		e.printStackTrace();//added only for debugging
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
	}
	
	
	
	
	

}
