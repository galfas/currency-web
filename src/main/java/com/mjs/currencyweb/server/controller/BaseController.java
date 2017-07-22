package com.mjs.currencyweb.server.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class BaseController {

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIlegalArgumentException() {
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({RuntimeException.class, IOException.class})
  public ResponseEntity<Object> handleException() {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}