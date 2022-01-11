package com.imdb.movieManager.services.impls;

import com.imdb.movieManager.daos.ActorDAO;
import com.imdb.movieManager.services.ActorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultActorService implements ActorService {

    @Override
    public List<String> getAllMoviesOfActor(Long actorId) {
        List<String> listOfMovies = new ArrayList<>();
        listOfMovies.add("ABC");
        listOfMovies.add("DEF");
        return listOfMovies;
    }

    @Override
    public ActorDAO getActorIdByActorName(String actorName) {

        return null;
    }

}
