package com.agb.recipe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        log.error("Duplicate Recipe Exception: {}", rnfEx.getMessage());

        return new ErrorResponse(rnfEx.getMessage());
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
