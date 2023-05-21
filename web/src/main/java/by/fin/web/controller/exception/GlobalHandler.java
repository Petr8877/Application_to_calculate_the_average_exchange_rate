package by.fin.web.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;


@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler({
            IllegalArgumentException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class
    })
    public ResponseEntity catchSingleErrorResponse(Exception e) {
        String message = e.getMessage();
        if (e.getClass().equals(HttpMessageNotReadableException.class)) {
            message = "Wrong form, check all fields";
        }
        if (e.getClass().equals(MethodArgumentNotValidException.class)){
            message = "The year must be no more than 2023.Months from 1 to 12.";
        }
        return ResponseEntity.status(400).body(message);
    }
}
