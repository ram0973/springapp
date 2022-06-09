package com.me.springapp.service;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.model.Article;
import com.me.springapp.model.ModelState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ArticleService {
    ResponseEntity<PagedArticlesDTO> findAll(String title, int page, int size, String[] sort);
    ResponseEntity<PagedArticlesDTO> findAllByState(String title, int page, int size, String[] sort, ModelState state);
    ResponseEntity<Article> findById(int id);
    ResponseEntity<Article> findByIdAndState(int id, ModelState state);
    ResponseEntity<Article> createArticle(ArticleDTO articleDTO);
    ResponseEntity<Article> updateArticle(int id, ArticleDTO articleDTO);
    ResponseEntity<HttpStatus> deleteArticle(int id);
    ResponseEntity<HttpStatus> softDeleteArticle(int id);
}
