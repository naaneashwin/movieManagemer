package com.imdb.movieManager.services;

import com.imdb.movieManager.daos.MovieDAO;
import com.imdb.movieManager.models.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDAO addMovie(MovieDTO movieDTO);

    boolean deleteMovie(Long movieId);

    MovieDAO updateCompleteMovie(MovieDTO movieDTO, Long movieId);

    List<String> getAllMoviesByGenre(String movieGenre);

    List<String> getAllMoviesByGenreAndReleasedYear(String movieGenre, Integer releasedYear);

    List<String> getAllMoviesBasedOnRating(Float rating);

    Float getRatingOfMovie(Long movieId);

    List<MovieDAO> getAllMoviesByActor(Long actorId);

    MovieDAO updateMovieRating(Long movieId, Float rating);

    MovieDAO updateMovieActors(Long movieID, List<Long> actorList);

    MovieDAO updateMovieRatingAndActors(Long movieId, Float rating, List<Long> actorList);
}
