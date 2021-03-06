package com.agb.recipe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.agb.recipe.controller.domain.ErrorResponse;
import com.agb.recipe.storage.exception.DuplicateRecipeException;
import com.agb.recipe.storage.exception.RecipeNotFoundException;

@ControllerAdvice(assignableTypes = RecipeController.class)
public class RecipeControllerAdvice
{

    private static Logger log = LoggerFactory.getLogger(RecipeControllerAdvice.class);

    @ExceptionHandler(DuplicateRecipeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse duplicateRecipeException (DuplicateRecipeException drEx)
    {
        log.error("Duplicate Recipe Exception: {}", drEx.getMessage());

        return new ErrorResponse(drEx.getMessage());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse recipeNotFoundException (RecipeNotFoundException rnfEx)
    {
        log.error("Recipe Not Found Exception: {}", rnfEx.getMessage());

        return new ErrorResponse(rnfEx.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidException (MethodArgumentNotValidException manvEx)
    {
        log.error("Validation Exception: {}", manvEx.getMessage());

        String errorMessage = null;
        for (ObjectError error : manvEx.getBindingResult().getAllErrors())
        {
            errorMessage = error.getDefaultMessage();
        }
        return new ErrorResponse(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse exception (Exception ex)
    {
        log.error("Exception (not sure what happened): {}", ex.getMessage());

        return new ErrorResponse(ex.getMessage());
    }
}
