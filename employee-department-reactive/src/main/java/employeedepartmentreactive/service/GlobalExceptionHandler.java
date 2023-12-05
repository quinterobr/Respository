package employeedepartmentreactive.service;

import employeedepartmentreactive.model.error.CustomErrorException;
import employeedepartmentreactive.model.error.EmployeeNotFoundException;
import employeedepartmentreactive.model.error.ErrorResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        var errorResponse = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        var errorResponse = buildErrorResponse(HttpStatus.NOT_FOUND,
                String.format("employee with id %s not found", ex.getEmployeeId())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(CustomErrorException.class)
    protected ResponseEntity<ErrorResponse> handleCustomError(RuntimeException ex) {
        var customErrorException = (CustomErrorException) ex;
        return ResponseEntity.status(customErrorException.getErrorResponse().getStatus())
                .body(customErrorException.getErrorResponse());
    }


    private static ErrorResponse buildErrorResponse(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .traceId(RandomStringUtils.randomAlphanumeric(10))
                .message(message)
                .timestamp(OffsetDateTime.now())
                .status(status)
                .build();
    }
}
