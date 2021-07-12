package com.movie.service;

import com.movie.dto.*;
import com.movie.entity.Movie;
import com.movie.repo.ActorRepository;
import com.movie.repo.CategoryRepository;
import com.movie.repo.MediaRepository;
import com.movie.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


@Service
public class MovieService {

    private MovieRepository movieRepository;
    private CategoryRepository categoryRepository;
    private MediaRepository mediaRepository;
    private ActorRepository actorRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        CategoryRepository categoryRepository,
                        MediaRepository mediaRepository,
                        ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.mediaRepository = mediaRepository;
        this.actorRepository = actorRepository;
    }



    public MovieResponse save(CreateMovieRequest request){
      return   MovieResponse.getInstance(movieRepository.save(request.convertMovie()));
    }

    public List<MovieResponse> getAll(){
        return movieRepository.findAll().stream()
                .map(movie ->MovieResponse.getInstance(movie) )
                .collect(Collectors.toList());
    }

    public Page<MovieResponse> getByPageable(Pageable pageable){
        return movieRepository.findAll(pageable).map(MovieResponse::getInstance);
    }

    public Page<MovieResponse> getByKeyword(String keyword, Pageable pageable){
        return movieRepository.getByKeyword(keyword,pageable)
                .map(MovieResponse::getInstance);
    }

    public Page<MovieResponse> getByFilter(HashMap<String,Object> filter,Pageable pageable){
         return movieRepository.findAll((Specification<Movie>)(root,cq,cb)->{
            Predicate p = cb.conjunction();
            if (filter.get("category")!=null)
                p=cb.and(p,cb.equal(root.get("category"),
                        categoryRepository.findById((Long)filter.get("category")).orElse(null)));
            if(filter.get("name")!=null)
                p=cb.and(p,cb.equal(root.get("name"),(String)filter.get("name")));
            if(filter.get("media")!=null)
                p=cb.and(p,cb.equal(root.get("media"),
                        mediaRepository.findById((Long)filter.get("media")).orElse(null)));
            if(filter.get("actor")!=null)
                p=cb.and(p,cb.equal(root.join("actors"),
                        actorRepository.findById((Long)filter.get("actor")).orElse(null)));
            return p;
        },pageable).map(MovieResponse::getInstance);

    }

    public MovieResponse getById(Long id){
        Movie movie=movieRepository.findById(id).orElse(null);
        if (movie==null)return null;
        return MovieResponse.getInstance(movie);
    }

    public void update(UpdateMovieRequest request){
        movieRepository.save(request.getMovie());
    }

    public void delete(Long id){
        Movie movie=movieRepository.findById(id).orElse(null);
        if (movie!=null)
            movieRepository.delete(movie);
    }

}
