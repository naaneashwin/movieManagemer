package com.imdb.movieManager.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.movieManager.daos.ActorDAO;
import com.imdb.movieManager.models.ActorDTO;
import com.imdb.movieManager.models.MovieDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ActorDataBase {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static Map<Long, ActorDAO> actorMap = new HashMap<>();

    public ActorDAO createActor(Long actorId, ActorDTO actorDTO){
        ActorDAO actorDAO = objectMapper.convertValue(actorDTO, ActorDAO.class);
        if(actorId==null){
            actorId = new Random().nextLong();
        }
        actorMap.put(actorId, actorDAO);
        return actorDAO;
    }

    public ActorDAO updateActor(final Long actorId, MovieDTO actorDTO){
        ActorDAO actorDAO = objectMapper.convertValue(actorDTO, ActorDAO.class);
        actorMap.replace(actorId, actorDAO);
        return actorDAO;
    }
}
