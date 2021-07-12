package com.movie.dto;

import com.movie.entity.Actor;
import com.movie.entity.Category;
import com.movie.entity.Media;
import com.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieRequest {
    @NotEmpty
    @NotNull
    @Size(min = 1,message = "Film adı karekter içermeli!")
    private String name;
    @Min(value = 4,message = "Tanımsız yıl değeri(örnek:1923)!")
    private int year;
    @NotEmpty
    @NotNull
    @Size(min = 5,message = "Açıklamala en az 5 karekter içermeli!")
    private String explanation;
    private List<Long> actorsId;
    private Long categoryId;
    private Long mediaId;

    public Movie convertMovie(){
        Movie movie=new Movie();
        movie.setName(this.name);
        movie.setYear(this.year);
        movie.setExplanation(this.explanation);
        if (this.mediaId!=null && this.mediaId>0){
            Media media=new Media();
            media.setId(this.mediaId);
            movie.setMedia(media);
        }
        if (this.categoryId!=null && this.categoryId>0){
            Category category=new Category();
            category.setId(this.categoryId);
            movie.setCategory(category);
        }
        if (this.actorsId!=null){
            movie.setActors(actorsId.stream().map(id->{
                Actor actor=new Actor();
                actor.setId(id);
                return actor;
            }).collect(Collectors.toList()));
        }
        return movie;
    }
}
