package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.service.exceptions.DatabaseException;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleNotFound(ObjectNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DatabaseException.class)
    public ModelAndView handleDataBaseException(DatabaseException exception) {
        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
