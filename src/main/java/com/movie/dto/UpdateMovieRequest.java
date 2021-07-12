package com.movie.dto;

import com.movie.entity.Actor;
import com.movie.entity.Category;
import com.movie.entity.Media;
import com.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieRequest {
    @NotNull
    private Long id;
    @Size(min = 1,message = "Film adı karekter içermeli!")
    private String name;
    @Min(4)
    private int year;
    @Size(min = 5,message = "Açıklamala en az 5 karekter içermeli!")
    private String explanation;
    private List<Long> actorsId;
    private Long categoryId;
    private Long mediaId;
    public Movie getMovie(){
        Movie movie=new Movie();
        movie.setId(this.id);
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
