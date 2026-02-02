package com.CloudCredits.Talkdesk.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobleException {
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException e, WebRequest req){
		
		ErrorDetail err=new ErrorDetail(e.getMessage(), req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
		
	}

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest req){
		
		ErrorDetail err=new ErrorDetail(e.getMessage(), req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
		
	}
	
}
