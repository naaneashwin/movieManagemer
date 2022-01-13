package com.imdb.movieManager.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.movieManager.daos.MovieDAO;
import com.imdb.movieManager.models.MovieDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MovieDataBase {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static Map<Long, MovieDAO> movieMap = new HashMap<>();

    public MovieDAO createMovie(Long movieId, MovieDTO movieDTO){
        MovieDAO movieDAO = objectMapper.convertValue(movieDTO, MovieDAO.class);
        if(movieId==null){
            movieId = new Random().nextLong();
        }
        movieMap.put(movieId, movieDAO);
        return movieDAO;
    }

    public MovieDAO updateMovie(final Long movieId, MovieDTO movieDTO){
        MovieDAO movieDAO = objectMapper.convertValue(movieDTO, MovieDAO.class);
        movieMap.replace(movieId, movieDAO);
        return movieDAO;
    }

}
