package com.imdb.movieManager.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.movieManager.daos.ActorDAO;
import com.imdb.movieManager.daos.MovieDAO;
import com.imdb.movieManager.exceptions.AuthTokenMissingException;
import com.imdb.movieManager.exceptions.MovieNameMissingException;
import com.imdb.movieManager.models.ApiResponse;
import com.imdb.movieManager.models.ErrorData;
import com.imdb.movieManager.models.MovieDTO;
import com.imdb.movieManager.models.UpdateDTO;
import com.imdb.movieManager.services.ActorService;
import com.imdb.movieManager.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
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

    @PostConstruct
    private void init (){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @PostMapping
    public ResponseEntity<ApiResponse> addMovie(@RequestBody MovieDTO movieDTO,
                                                HttpServletRequest httpServletRequest) throws Exception {



        String authToken = httpServletRequest.getHeader("X-Auth-Token");

        ApiResponse apiResponse = new ApiResponse();

        if(!StringUtils.hasText(authToken)){
            throw new AuthTokenMissingException("Auth Token Missing");
        }

        String movieName = movieDTO.getMovieName();

//        if(!StringUtils.hasText(movieName)){
//            ErrorData errorData = new ErrorData();
//            errorData.setErrorMessage("Movie Name Missing");
//            errorData.setErrorCode("Err002");
//            apiResponse.setError(errorData);
//            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//        }
        if(!StringUtils.hasText(movieName)){
            throw new MovieNameMissingException("Movie name Missing");
        }

        MovieDAO movieDAO = movieService.addMovie(movieDTO);

        MovieDTO resp = objectMapper.convertValue(movieDAO,MovieDTO.class);

        apiResponse.setData(resp);

        Long movieId = movieDAO.getMovieId();

        if(movieId!=null) return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        else return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);


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
    public ResponseEntity<ApiResponse> updateCompleteMovie(@PathVariable("movieId") Long movieId,
                                                           @RequestBody MovieDTO movieDTO){

        MovieDAO movieDAO = movieService.updateCompleteMovie(movieDTO, movieId);

        MovieDTO movieDTO1 = objectMapper.convertValue(movieDAO, MovieDTO.class);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieDTO1);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("{movieGenre}")
    public ResponseEntity<ApiResponse> getAllMoviesByGenre(@PathVariable("movieGenre") String movieGenre){
        List<String> movieList = movieService.getAllMoviesByGenre(movieGenre);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(params = {"movieGenre", "releasedYear"})
    public ResponseEntity<ApiResponse> getAllMoviesByGenreAndReleasedYear(@RequestParam("movieGenre") String movieGenre,
                                                                         @RequestParam("releasedYear") Integer releasedYear){

        List<String> movieList = movieService.getAllMoviesByGenreAndReleasedYear(movieGenre, releasedYear);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(params = "rating")
    public ResponseEntity<ApiResponse> getAllMoviesBasedOnRating(@RequestParam("rating") Float rating){
        List<String> movieList = movieService.getAllMoviesBasedOnRating(rating);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(params = "movieId")
    public ResponseEntity<ApiResponse> getRatingOfMovie(@RequestParam("movieId") Long movieId){
        Float result = movieService.getRatingOfMovie(movieId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(params = "actorName")
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

    @PatchMapping("{movieId}")
    public ResponseEntity<ApiResponse> updateMovieActors(@PathVariable("movieId") Long movieId,
                                                         @RequestBody UpdateDTO updateDTO,
                                                         HttpServletRequest httpServletRequest) throws Exception{

        String request = httpServletRequest.getHeader("X-Auth-Token");
        Integer actorListSize = updateDTO.getActors().size();
        Float rating = updateDTO.getRating();

        if(!StringUtils.hasText(request)) throw new AuthTokenMissingException("Auth Token Missing");

        if(rating!=null && actorListSize!=null){

            MovieDAO movieDAO = movieService.updateMovieRatingAndActors(movieId, rating, updateDTO.getActors());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData(movieDAO);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        }

        else if(rating!=null){
            MovieDAO movieDAO = movieService.updateMovieRating(movieId, rating);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData(movieDAO);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        else if(actorListSize != null){
            MovieDAO movieDAO = movieService.updateMovieActors(movieId, updateDTO.getActors());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData(movieDAO);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        else{
            ErrorData errorData = new ErrorData();
            errorData.setErrorCode("Err004");
            errorData.setErrorMessage("No Actors added and No rating Modified");
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setError(errorData);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }
}
