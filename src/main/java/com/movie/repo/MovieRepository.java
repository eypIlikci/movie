package com.movie.repo;

import com.movie.entity.Actor;
import com.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>,
        JpaSpecificationExecutor<Movie> {
    @Query("select m from Movie  m where lower(m.name) like lower(concat('%',?1,'%') )")
    Page<Movie> getByKeyword(String keyword, Pageable pageable);
    @Query("select m from Movie m join m.actors a where a= :actor")
    List<Movie> getByActors(Actor actor);
}
