package com.imdb.movieManager.models;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDTO {
    private Float rating;
    private List<Long> actors;
}
