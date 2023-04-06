package com.evgenys.online.shop.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//обрабатываем исключения во всех контроллерах
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        ShopError err = new ShopError(HttpStatus.NOT_FOUND.value(), e.getMessage());//обернем в JSON
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);//закинем со статусом
    }
}
