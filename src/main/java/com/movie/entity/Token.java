package com.movie.entity;

import com.movie.entity.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor

@NoArgsConstructor
@Entity
@Table(name = "_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private LocalDateTime createDate;
    private LocalDateTime exDate;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private String token;

}
