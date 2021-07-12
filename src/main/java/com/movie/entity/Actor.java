package com.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_actor")
public class Actor {
    @SequenceGenerator(sequenceName = "actor_seq",
            name = "actor_seq",
            allocationSize = 1,initialValue = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "actor_seq")
    private Long id;
    private String name;
}
