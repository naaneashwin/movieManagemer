package com.imdb.movieManager.models;

import lombok.Data;

import java.util.List;

@Data
public class MovieDTO {
    private String movieName;
    private String movieGenre;
    private Integer releasedYear;
    private String primaryLanguage;
    private List<ActorDTO> listOfActors;
    private Float rating;
}
