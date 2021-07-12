package com.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_category")
public class Category {

    @SequenceGenerator(sequenceName = "cat_seq",
            name = "cat_seq",
            allocationSize = 1,initialValue = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cat_seq")
    private Long id;
    private String name;
}
