package com.movie.repo;

import com.movie.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    @Query("select c from Category  c where lower(c.name) like lower(concat('%',?1,'%') )")
    Page<Category> findByKeyword(String keyword, Pageable pageable);
}
