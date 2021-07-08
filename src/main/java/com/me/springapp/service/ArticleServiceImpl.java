package com.me.springapp.service;

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

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private final ArticleRepository repository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.DESC;
    }

    public List<Sort.Order> getSortOrders(String [] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (!sort[0].contains(",")) {
            // sorting single column
            // ?sort=column1,direction1
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            return orders;
        }
        // will sort more than 2 columns
        // ?sort=column1,direction1&sort=column2,direction2
        for (String sortOrder : sort) {
            String[] _sort = sortOrder.split(",");
            orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
        }
        return orders;
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAll(String title, int page, int size, String[] sort)  {
        try {
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
                Map<String, Object> response = new HashMap<>();
                response.put("articles", articles);
                response.put("currentPage", pagedArticles.getNumber());
                response.put("totalItems", pagedArticles.getTotalElements());
                response.put("totalPages", pagedArticles.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Article> findById(int id) {
        Optional<Article> article = repository.findById(id);
        // return article
        //          .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        //          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return ResponseEntity.of(article);
    }

    @Override
    public ResponseEntity<Article>findByIdAndActive(int id) {
        Optional<Article> article = repository.findByIdAndActiveIsTrue(id);
        return ResponseEntity.of(article);
    }

    @Override
    public ResponseEntity<Article> createArticle(Article article) {
        try {
            Article _article = repository.save(new Article(article.getTitle(), article.getExcerpt(),
                    article.getContent(), article.isActive(), article.getUser()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Article> updateArticle(int id, Article article) {
        try {
            Optional<Article> _articleOptional = repository.findById(id);
            if (_articleOptional.isPresent()) {
                Article _article = _articleOptional.get();
                _article.setTitle(article.getTitle());
                _article.setExcerpt(article.getExcerpt());
                _article.setContent(article.getContent());
                _article.setActive(article.isActive());
                _article.setUser(article.getUser());
                return new ResponseEntity<>(repository.save(_article), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteArticle(int id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByActive(String title, int page, int size, String[] sort) {
        try {
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
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
