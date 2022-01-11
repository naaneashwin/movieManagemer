package com.imdb.movieManager.controllerAdvisors;

import com.imdb.movieManager.models.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvisor {

    @ExceptionHandler({AuthTokenMissingExcetion.class})
    public ResponseEntity<ApiResponse> handleAuthTokenMissingException(AuthTokenMissingExcetion authTokenMissingExcetion){
        Error error = new Error();
        error.setErrorCode("ERROR001");
        error.setErrorMessage(authTokenMissingExcetion.getMessage());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(error);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
