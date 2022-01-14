package com.imdb.movieManager.controllerAdvisors;

import com.imdb.movieManager.controllers.ActorController;
import com.imdb.movieManager.exceptions.ActorNameMissingException;
import com.imdb.movieManager.models.ApiResponse;
import com.imdb.movieManager.models.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(assignableTypes = ActorController.class)
public class ActorControllerAdvisor {

    @ExceptionHandler(ActorNameMissingException.class)
    public ResponseEntity<ApiResponse> handleAuthorNameException(ActorNameMissingException actorNameMissingException){
        ErrorData errorData = new ErrorData();
        errorData.setErrorMessage(actorNameMissingException.getMessage());
        errorData.setErrorCode("Err003");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(errorData);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
