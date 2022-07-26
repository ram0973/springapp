package com.me.springapp.service;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.model.Article;
import com.me.springapp.model.ArticleState;

import java.util.Optional;

public interface ArticleService {
    Optional<PagedArticlesDTO> findAll(String title, int page, int size, String[] sort);

    Optional<PagedArticlesDTO> findAllByState(String title, int page, int size, String[] sort, ArticleState state);

    Optional<Article> findById(int id);

    Optional<Article> findByIdAndState(int id, ArticleState state);

    Optional<Article> createArticle(ArticleDTO articleDTO);

    Optional<Article> updateArticle(int id, ArticleDTO articleDTO);

    void deleteArticle(int id);

    Optional<Article> softDeleteArticle(int id);
}
