package com.me.springapp.service;

import com.me.springapp.model.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    ResponseEntity<Map<String, Object>> findAll(String title, int page, int size, String[] sort);
    ResponseEntity<Article> findById(int id);
    ResponseEntity<Article> findByIdAndActive(int id);
    ResponseEntity<Article> createArticle(Article article);
    ResponseEntity<Article> updateArticle(int id, Article article);
    ResponseEntity<HttpStatus> deleteArticle(int id);
    ResponseEntity<Map<String, Object>> findAllByActive(String title, int page, int size, String[] sort);
}
