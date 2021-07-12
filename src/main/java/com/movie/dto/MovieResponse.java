package com.movie.dto;

import com.movie.entity.Actor;
import com.movie.entity.Category;
import com.movie.entity.Media;
import com.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private Long id;
    private String name;
    private int year;
    private String explanation;
    private List<Long> actorsId;
    private Long categoryId;
    private Long mediaId;
    public static MovieResponse getInstance(Movie movie){
        MovieResponse response=new MovieResponse();
        response.setName(movie.getName());
        response.setId(movie.getId());
        response.setExplanation(movie.getExplanation());
        response.setYear(movie.getYear());
        if (movie.getCategory()!=null)
            response.setCategoryId(movie.getCategory().getId());
        if (movie.getMedia()!=null)
            response.setMediaId(movie.getMedia().getId());
        if (movie.getActors()!=null)
            response.setActorsId(movie.getActors().stream()
                    .map(actor->actor.getId()).collect(Collectors.toList()));
        return response;
    }


}
