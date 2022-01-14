package com.imdb.movieManager.exceptions;

public class AuthTokenMissingException extends Exception{

    public AuthTokenMissingException(String message){
        super(message);
    }

}
