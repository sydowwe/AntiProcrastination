package com.timeOrganizer.controller;

import com.timeOrganizer.exception.BatchDeleteException;
import com.timeOrganizer.exception.IdInTokenFormatException;
import com.timeOrganizer.exception.QrCode2FAGenerationException;
import com.timeOrganizer.exception.UserNotInSecurityContext;
import com.timeOrganizer.model.dto.response.ErrorResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return this.createErrorResponse("Unspecified exception", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundExceptionException(EntityNotFoundException e) {
        return this.createErrorResponse("Entity not found", HttpStatus.NOT_FOUND, e);
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException e) {
        return this.createErrorResponse("Entity already exists", HttpStatus.CONFLICT, e);
    }
   /* @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<ErrorResponse> handleEntityURISyntaxException(URISyntaxException e) {
        return this.createErrorResponse("URI Syntax", HttpStatus.NOT_FOUND, e);
    }*/
    @ExceptionHandler(UserNotInSecurityContext.class)
    public ResponseEntity<ErrorResponse> handleUserNotInSecurityContextException(UserNotInSecurityContext e) {
        return this.createErrorResponse("User not in security context", HttpStatus.FAILED_DEPENDENCY, e);
    }
    @ExceptionHandler(IdInTokenFormatException.class)
    public ResponseEntity<ErrorResponse> handleIdInTokenFormatException(IdInTokenFormatException e) {
        return this.createErrorResponse("Id in token format", HttpStatus.FAILED_DEPENDENCY, e);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return this.createErrorResponse("Wrong credentials", HttpStatus.FORBIDDEN, e);
    }
    @ExceptionHandler(QrCode2FAGenerationException.class)
    public ResponseEntity<ErrorResponse> handleQrCode2FAGenerationException(QrCode2FAGenerationException e) {
        return this.createErrorResponse("2FA QR code generation", HttpStatus.FAILED_DEPENDENCY, e);
    }
    @ExceptionHandler(BatchDeleteException.class)
    public ResponseEntity<ErrorResponse> handleBatchDeleteException(BatchDeleteException e) {
        return this.createErrorResponse("Batch delete failed", HttpStatus.MULTI_STATUS, e);
    }
    private ResponseEntity<ErrorResponse> createErrorResponse(String error, HttpStatus status,Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(error+"  ERROR!", e.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }
    // Add more @ExceptionHandler methods for other specific exceptions if needed
}