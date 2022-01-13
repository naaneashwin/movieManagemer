package com.imdb.movieManager.models;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class MovieDTO {
    @NonNull
    private String movieName;
    private String movieGenre;
    private Integer releasedYear;
    private String primaryLanguage;
    private List<ActorDTO> listOfActors;
    private Float rating;
}
