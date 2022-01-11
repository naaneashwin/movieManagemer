package com.imdb.movieManager.daos;

import lombok.Data;

import java.util.List;
@Data
public class MovieDAO {
    private Long movieId;
    private String movieName;
    private String movieGenre;
    private Integer releasedYear;
    private String primaryLanguage;
    private List<ActorDAO> listOfActors;
    private Float rating;
}
