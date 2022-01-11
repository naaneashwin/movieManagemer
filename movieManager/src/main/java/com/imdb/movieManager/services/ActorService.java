package com.imdb.movieManager.services;

import com.imdb.movieManager.daos.ActorDAO;

import java.util.List;

public interface ActorService {
    List<String> getAllMoviesOfActor(Long actorId);

    ActorDAO getActorIdByActorName(String actorName);
}
