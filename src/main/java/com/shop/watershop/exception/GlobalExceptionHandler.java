package com.shop.watershop.exception;

import com.shop.watershop.models.User;
import com.shop.watershop.web.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(Exception ex,@SessionAttribute("user") User user) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", user);
        modelAndView.addObject("emailError", "Email already registered");

        return modelAndView;

    }
}
