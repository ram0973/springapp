package com.me.springapp.service;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.ArticleMapper;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.exceptions.NoSuchArticleException;
import com.me.springapp.model.Article;
import com.me.springapp.model.ModelState;
import com.me.springapp.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService, PagedEntityUtils {

    private final ArticleRepository articleRepository;

    private Optional<PagedArticlesDTO> getPagedArticlesDTO(Page<Article> pagedArticles) {
        List<Article> articles = pagedArticles.getContent();
        if (articles.isEmpty()) {
            return Optional.empty();
        } else {
            PagedArticlesDTO pagedArticlesDTO = new PagedArticlesDTO(articles, pagedArticles.getNumber(),
                pagedArticles.getTotalElements(), pagedArticles.getTotalPages());
            return Optional.of(pagedArticlesDTO);
        }
    }

    @Override
    public Optional<PagedArticlesDTO> findAll(String title, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = articleRepository.findAll(pageable);
        } else {
            pagedArticles = articleRepository.findAllByTitleContaining(title, pageable);
        }
        if (pagedArticles.getContent().isEmpty()) {
            return Optional.empty();
        }
        return getPagedArticlesDTO(pagedArticles);
    }

    @Override
    public Optional<Article> findById(int id) {
        return articleRepository.findById(id);
    }

    @Override
    public Optional<Article> findByIdAndState(int id, ModelState state) {
        return articleRepository.findByIdAndState(id, state);
    }

    @Override
    public Optional<Article> createArticle(ArticleDTO articleDTO) {
        Article article = ArticleMapper.articleFromDto(articleDTO);
        Article savedArticle = articleRepository.save(article);
        return Optional.of(savedArticle);
    }

    @Override
    public Optional<Article> updateArticle(int id, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id).orElseThrow(NoSuchArticleException::new);
        ArticleMapper.updateArticleFromDto(article, articleDTO);
        return Optional.of(articleRepository.save(article));
    }

    @Override
    public void deleteArticle(int id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Optional<Article> softDeleteArticle(int id) {
        Article article = articleRepository.findById(id).orElseThrow(NoSuchArticleException::new);
        article.setState(ModelState.SOFT_DELETED);
        return Optional.of(articleRepository.save(article));
    }

    @Override
    public Optional<PagedArticlesDTO> findAllByState(String title, int page, int size,
                                                     String[] sort, ModelState state) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<Article> pagedArticles;
        if (title == null || title.isBlank()) {
            pagedArticles = articleRepository.findAllByState(state, pageable);
        } else {
            pagedArticles = articleRepository.findAllByTitleContainingAndState(title, state, pageable);
        }
        return getPagedArticlesDTO(pagedArticles);
    }
}
