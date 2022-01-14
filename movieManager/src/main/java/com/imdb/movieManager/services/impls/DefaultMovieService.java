package com.imdb.movieManager.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.movieManager.daos.MovieDAO;
import com.imdb.movieManager.database.MovieDataBase;
import com.imdb.movieManager.models.MovieDTO;
import com.imdb.movieManager.services.MovieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MovieDAO addMovie(MovieDTO movieDTO) {

        return new MovieDataBase().createMovie(null,movieDTO);

    }

    private static boolean isPresent(Long movieId){
        //if movieId id present return true else false
        return true;
    }

    @Override
    public boolean deleteMovie(Long movieId) {
        boolean flag = isPresent(movieId);
        //if flag is true, delete the data and return true else false;
        if(flag) return true;
        else return false;
    }

    @Override
    public MovieDAO updateCompleteMovie(MovieDTO movieDTO, Long movieId) {
        return new MovieDataBase().updateMovie(movieId, movieDTO);
    }

    @Override
    public List<String> getAllMoviesByGenre(String movieGenre) {
        List<String> movieList = new ArrayList<>();
        return movieList;
    }

    @Override
    public List<String> getAllMoviesByGenreAndReleasedYear(String movieGenre, Integer releasedYear) {

        List<String> movieList = new ArrayList<>();

        return movieList;
    }

    @Override
    public List<String> getAllMoviesBasedOnRating(Float rating) {

        List<String> movieList = new ArrayList<>();

        return movieList;
    }

    @Override
    public Float getRatingOfMovie(Long movieId) {
        return 3.5f;
    }

    @Override
    public List<MovieDAO> getAllMoviesByActor(Long actorId) {
        List<MovieDAO> movieDAOList = new ArrayList<>();
        return movieDAOList;
    }

    @Override
    public MovieDAO updateMovieRating(Long movieId, Float rating) {
        return null;
    }

    @Override
    public MovieDAO updateMovieActors(Long movieID, List<Long> actorList) {
        return null;
    }

    @Override
    public MovieDAO updateMovieRatingAndActors(Long movieId, Float rating, List<Long> actorList) {
        return null;
    }
}
