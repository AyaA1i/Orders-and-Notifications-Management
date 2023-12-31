package com.onm.ordersandnotificationsmanagement.utilities;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Exception handler.
 */
public class ErrorHandler {
    /**
     * Gets error handler map.
     *
     * @param ex the ex
     * @return the error handler map
     */
    public static Map<String, String> getErrorHandlerMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
