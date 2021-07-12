package com.movie.repo;

import com.movie.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor,Long> {
    Optional<Actor> findByName(String name);
    @Query("select a from Actor  a where lower(a.name) like lower(concat('%',?1,'%') )")
    Page<Actor> getByKeyword(String keyword, Pageable pageable);
}
