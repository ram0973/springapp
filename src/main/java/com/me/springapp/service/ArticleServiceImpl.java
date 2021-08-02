package com.me.springapp.service;

import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.model.Article;
import com.me.springapp.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService, PagedEntity {

    private final ArticleRepository repository;

    private ResponseEntity<PagedArticlesDTO> getPagedArticlesDTOResponseEntity(Page<Article> pagedArticles) {
        List<Article> articles = pagedArticles.getContent();
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            PagedArticlesDTO response = new PagedArticlesDTO(articles, pagedArticles.getNumber(),
                pagedArticles.getTotalElements(), pagedArticles.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<PagedArticlesDTO> findAll(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntity.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = repository.findAll(pageable);
        } else {
            pagedArticles = repository.findAllByTitleContaining(title, pageable);
        }
        return getPagedArticlesDTOResponseEntity(pagedArticles);
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
    public ResponseEntity<PagedArticlesDTO> findAllByActive(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntity.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = repository.findAllByActiveIsTrue(pageable);
        } else {
            pagedArticles = repository.findAllByTitleContainingAndActiveIsTrue(title, pageable);
        }
        return getPagedArticlesDTOResponseEntity(pagedArticles);
    }
}
