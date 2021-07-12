package com.movie.service;

import com.movie.dto.ActorResponse;
import com.movie.dto.CreateActorRequest;
import com.movie.dto.MovieResponse;
import com.movie.dto.UpdateActorRequest;
import com.movie.entity.Actor;
import com.movie.entity.Movie;
import com.movie.repo.ActorRepository;
import com.movie.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private ActorRepository actorRepository;
    private MovieRepository movieRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository,
                        MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository=movieRepository;
    }

    public ActorResponse save(CreateActorRequest request){
       Actor actor=actorRepository.save(
               new Actor(null,request.getName())
            );
       return  new ActorResponse(actor.getId(),actor.getName());
    }
    public List<ActorResponse> getAll(){
        List<ActorResponse> list= actorRepository.findAll().stream()
                .map(actor->new ActorResponse(actor.getId(),actor.getName()))
                .collect(Collectors.toList());
        return list;
    }
    public Page<ActorResponse> getByPageable(Pageable pageable){
        return actorRepository.findAll(pageable)
                .map(actor -> new ActorResponse(actor.getId(),actor.getName()));
    }

    public Page<ActorResponse> getByKeyword(String keyword, Pageable pageable){
      return   actorRepository.getByKeyword(keyword,pageable)
              .map(actor->new ActorResponse(actor.getId(),actor.getName()));
    }

    public ActorResponse getById(Long id){
        Actor actor=actorRepository.findById(id).orElse(null);
        if(actor!=null)
            return new ActorResponse(actor.getId(),actor.getName());
        return null;
    }

    public void update(UpdateActorRequest request){
        actorRepository.save(new Actor(request.getId(),request.getName()));
    }

    public void delete(Long id){
        Actor actor=actorRepository.findById(id).orElse(null);
        movieRepository.getByActors(actor).stream()
                .forEach(movie -> {
                    movie.getActors().remove(actor);
                    movieRepository.save(movie);
                });
        if (actor!=null)
            actorRepository.delete(actor);
    }

}
