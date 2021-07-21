package com.me.springapp.service;

import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.model.Article;
import com.me.springapp.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.DESC;
    }

    public static List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (!sort[0].contains(",")) {
            // sorting single column
            // ?sort=column1,direction1
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            return orders;
        }
        // sort more than 2 columns
        // ?sort=column1,direction1&sort=column2,direction2
        for (String sortOrder : sort) {
            String[] sortArray = sortOrder.split(",");
            orders.add(new Sort.Order(getSortDirection(sortArray[1]), sortArray[0]));
        }
        return orders;
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAll(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = repository.findAll(pageable);
        } else {
            pagedArticles = repository.findAllByTitleContaining(title, pageable);
        }
        List<Article> articles = pagedArticles.getContent();
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            PagedArticlesDTO response = new PagedArticlesDTO(articles, pagedArticles.getNumber(),
                pagedArticles.getTotalElements(), pagedArticles.getTotalPages());
            return new ResponseEntity<PagedArticlesDTO>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Article> findById(int id) {
        Optional<Article> article = repository.findById(id);
        return ResponseEntity.of(article);
    }

    @Override
    public ResponseEntity<Article> findByIdAndActive(int id) {
        Optional<Article> article = repository.findByIdAndActiveIsTrue(id);
        return ResponseEntity.of(article);
    }

    @Override
    public ResponseEntity<Article> createArticle(Article article) {
        Article savedArticle = repository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Article> updateArticle(int id, Article article) {
        Optional<Article> articleOptional = repository.findById(id);
        if (articleOptional.isPresent()) {
            Article oldArticle = articleOptional.get();
            oldArticle.setTitle(article.getTitle());
            oldArticle.setExcerpt(article.getExcerpt());
            oldArticle.setContent(article.getContent());
            oldArticle.setActive(article.isActive());
            oldArticle.setUser(article.getUser());
            return new ResponseEntity<>(repository.save(oldArticle), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> deleteArticle(int id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByActive(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = repository.findAllByActiveIsTrue(pageable);
        } else {
            pagedArticles = repository.findAllByTitleContainingAndActiveIsTrue(title, pageable);
        }
        List<Article> articles = pagedArticles.getContent();
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("currentPage", pagedArticles.getNumber());
            response.put("totalItems", pagedArticles.getTotalElements());
            response.put("totalPages", pagedArticles.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
