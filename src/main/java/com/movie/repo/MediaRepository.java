package com.movie.repo;

import com.movie.entity.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media,Long> {
    Optional<Media> findByName(String name);
    @Query("select m from Media  m where lower(m.name) like lower(concat('%',?1,'%') )")
    Page<Media> findByKeyword(String keyword, Pageable pageable);
}
