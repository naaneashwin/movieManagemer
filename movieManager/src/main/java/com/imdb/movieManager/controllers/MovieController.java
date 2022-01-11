package com.imdb.movieManager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.movieManager.daos.ActorDAO;
import com.imdb.movieManager.daos.MovieDAO;
import com.imdb.movieManager.models.ApiResponse;
import com.imdb.movieManager.models.MovieDTO;
import com.imdb.movieManager.services.ActorService;
import com.imdb.movieManager.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    private ObjectMapper objectMapper= new ObjectMapper();

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorService actorService;

    @PostMapping
    public ResponseEntity<ApiResponse> addMovie(@RequestBody MovieDTO movieDTO) {

        MovieDAO movieDAO = movieService.addMovie(movieDTO);

        ApiResponse apiResponse = new ApiResponse();

        MovieDTO resp = objectMapper.convertValue(movieDAO,MovieDTO.class);

        apiResponse.setData(resp);

        Long movieId = movieDAO.getMovieId();

        if(movieId!=null) return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        else return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);


    }

    @DeleteMapping
    public ResponseEntity<String> deleteMovie(@RequestParam("movieId") Long movieId){

        boolean flag = movieService.deleteMovie(movieId);
        if(flag){
            return new ResponseEntity<>("Movie with ID "+movieId+" deleted", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Movie not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("{movieId}")
    public ResponseEntity<ApiResponse> updateCompleteMovie(@PathVariable("movieId") Long movieId,@RequestBody MovieDTO movieDTO){

        MovieDAO movieDAO = movieService.updateCompleteMovie(movieDTO, movieId);

        ApiResponse apiResponse = new ApiResponse();
        if(movieDAO!=null){
            apiResponse.setData(movieDAO);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        else {
            apiResponse.setData("No movie found");
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{genre}")
    public ResponseEntity<ApiResponse> getAllMoviesByGenre(@PathVariable("movieGenre") String movieGenre){
        List<String> movieList = movieService.getAllMoviesByGenre(movieGenre);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllMoviesByGenreAndReleasedYear(@RequestParam("movieGenre") String movieGenre,
                                                                         @RequestParam("releasedYear") Integer releasedYear){

        List<String> movieList = movieService.getAllMoviesByGenreAndReleasedYear(movieGenre, releasedYear);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{rating}")
    public ResponseEntity<ApiResponse> getAllMoviesBasedOnRating(@PathVariable("rating") Float rating){
        List<String> movieList = movieService.getAllMoviesBasedOnRating(rating);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getRatingOfMovie(@RequestParam("movieId") Long movieId){
        Float result = movieService.getRatingOfMovie(movieId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllMoviesByActor(@RequestParam("actorName") String actorName){

        ActorDAO actorDAO = actorService.getActorIdByActorName(actorName);

        List<MovieDAO> movieDAOList = movieService.getAllMoviesByActor(actorDAO.getActorId());
        List<MovieDTO> movieDTOList = new ArrayList<>();

        movieDAOList.forEach((movieDAO -> {
            MovieDTO movieDTO = objectMapper.convertValue(movieDAO,MovieDTO.class);
            movieDTOList.add(movieDTO);
        }));

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieDTOList);

        return ResponseEntity.ok(apiResponse);
    }
}
