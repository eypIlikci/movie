package com.movie.repo;

import com.movie.entity.Token;
import com.movie.entity.User;
import com.movie.entity.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("select t from Token  t where t.tokenType= :tokenType")
    List<Token> getByTokenType(TokenType tokenType);
    @Query("select t from Token t where t.token= :token")
    Optional<Token> findByToken(String token);
}
