package com.imdb.movieManager.controllerAdvisors;

import com.imdb.movieManager.controllers.MovieController;
import com.imdb.movieManager.exceptions.MovieNameMissingException;
import com.imdb.movieManager.models.ApiResponse;
import com.imdb.movieManager.models.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MovieController.class)
public class MovieControllerAdvisor {

    @ExceptionHandler(MovieNameMissingException.class)
    public ResponseEntity<ApiResponse> handleMovieNameMissingException(MovieNameMissingException movieNameMissingException){
        ErrorData errorData = new ErrorData();
        errorData.setErrorMessage(movieNameMissingException.getMessage());
        errorData.setErrorCode("Err002");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(errorData);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
