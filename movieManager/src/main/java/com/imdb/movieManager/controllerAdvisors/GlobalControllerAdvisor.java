package com.imdb.movieManager.controllerAdvisors;


import com.imdb.movieManager.exceptions.AuthTokenMissingException;
import com.imdb.movieManager.models.ApiResponse;
import com.imdb.movieManager.models.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvisor {

    @ExceptionHandler({AuthTokenMissingException.class})
    public ResponseEntity<ApiResponse> handleAuthTokenMissingException(AuthTokenMissingException authTokenMissingException){
        ErrorData errorData = new ErrorData();
        errorData.setErrorCode("Err001");
        errorData.setErrorMessage(authTokenMissingException.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(errorData);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
