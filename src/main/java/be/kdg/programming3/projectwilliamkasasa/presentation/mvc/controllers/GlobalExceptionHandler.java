package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.exception.CustomDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({DataAccessException.class, CustomDataAccessException.class})
    public String handleDatabaseException(Exception e){
        logger.error(e.getMessage());
        return "dberror";
    }
}


