package com.me.springapp.repository;

import com.me.springapp.model.Article;
import com.me.springapp.model.ModelState;
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

    Optional<Article> findByIdAndState(int id, ModelState state);
    @NonNull
    Page<Article> findAll(@NonNull Pageable pageable);

    Page<Article> findAllByTitleContaining(String title, Pageable pageable);

    Page<Article> findAllByState(ModelState state, Pageable pageable);

    Page<Article> findAllByTitleContainingAndState(String title, ModelState state, Pageable pageable);
}

