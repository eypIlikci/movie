package com.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_media")
public class Media {

    @SequenceGenerator(sequenceName = "media_seq",
            name = "media_seq",
            allocationSize = 1,initialValue = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "media_seq")
    private Long id;
    private String name;
}
