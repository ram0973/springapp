package com.me.springapp.service;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.ArticleMapper;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.exceptions.NoSuchArticleException;
import com.me.springapp.exceptions.NoSuchUsersException;
import com.me.springapp.model.Article;
import com.me.springapp.model.ModelState;
import com.me.springapp.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService, PagedEntityUtils {

    private final ArticleRepository articleRepository;

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
            pagedArticles = articleRepository.findAll(pageable);
        } else {
            pagedArticles = articleRepository.findAllByTitleContaining(title, pageable);
        }
        if (pagedArticles.getContent().isEmpty()) {
            throw new NoSuchUsersException();
        }
        return getPagedArticlesDTOResponseEntity(pagedArticles);
    }

    @Override
    public ResponseEntity<Article> findById(int id) {
        Optional<Article> article = articleRepository.findById(id);
        return ResponseEntity.of(article);
    }

    @Override
    public ResponseEntity<Article> findByIdAndActive(int id) {
        Optional<Article> article = articleRepository.findByIdAndActiveIsTrue(id);
        return ResponseEntity.of(article);
    }

    @Override
    public ResponseEntity<Article> createArticle(ArticleDTO articleDTO) {
        Article article = ArticleMapper.articleFromDto(articleDTO);
        Article savedArticle = articleRepository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Article> updateArticle(int id, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id).orElseThrow(NoSuchArticleException::new);
        ArticleMapper.updateArticleFromDto(article, articleDTO);
        return new ResponseEntity<>(articleRepository.save(article), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteArticle(int id) {
        articleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> softDeleteArticle(int id) {
        Article article = articleRepository.findById(id).orElseThrow(NoSuchArticleException::new);
        article.setState(ModelState.DELETED);
        articleRepository.save(article);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PagedArticlesDTO> findAllByActive(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = articleRepository.findAllByActiveIsTrue(pageable);
        } else {
            pagedArticles = articleRepository.findAllByTitleContainingAndActiveIsTrue(title, pageable);
        }
        return getPagedArticlesDTOResponseEntity(pagedArticles);
    }
}
