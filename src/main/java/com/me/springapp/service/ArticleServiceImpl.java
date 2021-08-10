package com.me.springapp.service;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.ArticleMapper;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.exceptions.NoSuchArticleException;
import com.me.springapp.exceptions.NoSuchUsersException;
import com.me.springapp.model.Article;
import com.me.springapp.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional
public class ArticleServiceImpl implements ArticleService, PagedEntityUtils {

    private final ArticleRepository repository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

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
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = repository.findAll(pageable);
        } else {
            pagedArticles = repository.findAllByTitleContaining(title, pageable);
        }
        if (pagedArticles.getContent().isEmpty()) {
            throw new NoSuchUsersException();
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
    public ResponseEntity<Article> createArticle(ArticleDTO articleDTO) {
        Article article = ArticleMapper.INSTANCE.articleFromDto(articleDTO);
        Article savedArticle = repository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Article> updateArticle(int id, ArticleDTO articleDTO) {
        Article article = repository.findById(id).orElseThrow(NoSuchArticleException::new);
        ArticleMapper.INSTANCE.updateArticleFromDto(articleDTO, article);
        return new ResponseEntity<>(repository.save(article), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteArticle(int id) {
        repository.findById(id).orElseThrow(NoSuchArticleException::new);
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PagedArticlesDTO> findAllByActive(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = repository.findAllByActiveIsTrue(pageable);
        } else {
            pagedArticles = repository.findAllByTitleContainingAndActiveIsTrue(title, pageable);
        }
        return getPagedArticlesDTOResponseEntity(pagedArticles);
    }
}
