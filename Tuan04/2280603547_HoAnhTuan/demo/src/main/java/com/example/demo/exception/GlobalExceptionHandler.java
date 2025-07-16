package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        if (model.asMap().containsKey("org.springframework.web.servlet.DispatcherServlet.CONTEXT")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("error", ex.getMessage());
            mav.setStatus(HttpStatus.NOT_FOUND);
            return mav;
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        if (model.asMap().containsKey("org.springframework.web.servlet.DispatcherServlet.CONTEXT")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("error", ex.getMessage());
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }
    }

    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception ex, Model model) {
        if (model.asMap().containsKey("org.springframework.web.servlet.DispatcherServlet.CONTEXT")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("error", "An unexpected error occurred: " + ex.getMessage());
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return mav;
        }
    }
}