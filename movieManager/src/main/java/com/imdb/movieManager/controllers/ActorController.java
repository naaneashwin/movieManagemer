package com.imdb.movieManager.controllers;

import com.imdb.movieManager.models.ApiResponse;
import com.imdb.movieManager.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("actors")
public class ActorController {

    @Autowired
    ActorService actorService;

    @GetMapping("{actorId}")
    public ResponseEntity<ApiResponse> getAllMoviesOfActor(@PathVariable("actorId") Long actorId){
        List<String> listOfMovies = actorService.getAllMoviesOfActor(actorId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(listOfMovies);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
