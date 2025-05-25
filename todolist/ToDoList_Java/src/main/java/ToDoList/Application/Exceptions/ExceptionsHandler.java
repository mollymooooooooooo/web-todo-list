package ToDoList.Application.Exceptions;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Exceptions.CustomExceptions.NotEnoughAccessException;
import ToDoList.Application.Repositories.ModelsDTO.Other.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static jdk.dynalink.linker.support.Guards.asType;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel> handleAllExceptions(Exception ex, WebRequest request) {
        ResponseModel response = new ResponseModel(
                "500",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KeyNotFoundException.class)
    public ResponseEntity<ResponseModel> handleKeyNotFoundException(Exception ex, WebRequest request) {
        ResponseModel response = new ResponseModel(
                "400",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughAccessException.class)
    public ResponseEntity<ResponseModel> handleNotEnoughAccessException(Exception ex, WebRequest request) {
        ResponseModel response = new ResponseModel(
                "403",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    //@ExceptionHandler(AccessDeniedException.class)
    //public ResponseEntity<ResponseModel> handleAccessDeniedException(AccessDeniedException ex) {
    //    ResponseModel response = new ResponseModel(
    //            "403",
    //            ex.getMessage()
    //    );
    //    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    //}
}
