package com.premraj.product.exception;

import com.premraj.product.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Message> badRequestHandler(ServiceException ex) {
        Message message = Message.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description(ex.getMessage())
                .build();
        log.error("badRequestHandler {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Message> internalServerErrorHandler(Exception ex) {
        Message message = Message.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description(ex.getMessage())
                .build();
        log.error("internalServerErrorHandler {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Message> formatHandler(NumberFormatException ex) {
        Message message = Message.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description("Invalid data in CSV, " + ex.getMessage())
                .build();
        log.error("formatHandler {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ArrayIndexOutOfBoundsException.class})
    public ResponseEntity<Message> arrayHandler(ArrayIndexOutOfBoundsException ex) {
        Message message = Message.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description("Invalid data in CSV, " + ex.getMessage())
                .build();
        log.error("arrayHandler {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmptyProductException.class})
    public ResponseEntity<Message> emptyProductHandler(EmptyProductException ex) {
        Message message = Message.builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .description(ex.getMessage())
                .build();
        log.error("emptyProductHandler {}", message);
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }


}