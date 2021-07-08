package com.me.springapp.repository;

import com.me.springapp.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Optional<Article> findById(int id);
    Optional<Article> findByIdAndActiveIsTrue(int id);

    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByTitleContaining(String title, Pageable pageable);

    Page<Article> findAllByActiveIsTrue(Pageable pageable);
    Page<Article> findAllByTitleContainingAndActiveIsTrue(String title, Pageable pageable);
}

