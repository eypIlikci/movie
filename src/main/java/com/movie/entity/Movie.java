package com.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_movie")
public class Movie {

    @SequenceGenerator(sequenceName = "movie_seq",
            name = "movie_seq",
            allocationSize = 1,initialValue = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "movie_seq")
    private Long id;
    private String name;
    private String explanation;
    private int year;
    @ManyToMany
    @JoinTable(name = "movie_actor")
    private List<Actor> actors;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Media media;


}
