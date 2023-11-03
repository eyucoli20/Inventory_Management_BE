package InventoryManagement.exception_handling;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = processFieldErrors(ex.getBindingResult());
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException ex) {
        Map<String, String> errorMap = processFieldErrors(ex.getBindingResult());
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateProductException(DuplicateProductException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadRequestException.class,
            IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "An unexpected error occurred while processing your request. Please try again later.";
        log.error("INTERNAL_SERVER_ERROR: " + ex.getMessage(), ex);
        ex.printStackTrace();
        return buildResponse(errorMessage, request, httpStatus);
    }

    private ResponseEntity<ExceptionResponse> buildResponse(String errorMessage, HttpServletRequest request, HttpStatus httpStatus) {
        ExceptionResponse apiException = new ExceptionResponse(LocalDateTime.now().toString(), httpStatus, errorMessage, request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(apiException);
    }

    private Map<String, String> processFieldErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }

}


