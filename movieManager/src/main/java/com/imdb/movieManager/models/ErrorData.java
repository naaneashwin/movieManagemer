package com.imdb.movieManager.models;

import lombok.Data;

@Data
public class ErrorData {
    private String errorMessage;
    private String errorCode;
}
