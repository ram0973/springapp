package com.me.springapp.repository;

import com.me.springapp.model.Article;
import com.me.springapp.model.ArticleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Optional<Article> findById(int id);

    Optional<Article> findByIdAndState(int id, ArticleState state);

    @NonNull
    Page<Article> findAll(@NonNull Pageable pageable);

    @NonNull
    Page<Article> findAllByTitleContaining(String title, @NonNull Pageable pageable);

    @NonNull
    Page<Article> findAllByState(ArticleState state, @NonNull Pageable pageable);

    @NonNull
    Page<Article> findAllByTitleContainingAndState(String title, ArticleState state, @NonNull Pageable pageable);
}

