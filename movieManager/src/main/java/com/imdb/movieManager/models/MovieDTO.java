package com.imdb.movieManager.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieDTO {

    private String movieName;
    private String movieGenre;
    private Integer releasedYear;
    private String primaryLanguage;
    private List<ActorDTO> listOfActors;
    private Float rating;

}
