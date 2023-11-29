package com.me.springapp.controller;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.Article;
import com.me.springapp.model.ArticleState;
import com.me.springapp.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") //TODO: check this
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<PagedArticlesDTO> getArticles(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        PagedArticlesDTO pagedArticlesDTO = articleService.findAll(title, page, size, sort)
            .orElseThrow(() -> new NoSuchEntityException("No such articles"));
        return ResponseEntity.ok(pagedArticlesDTO);
    }

    @GetMapping("/articles/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") int id) {
        Article article = articleService.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such article with id: " + id));
        return ResponseEntity.ok(article);
    }

    @PostMapping("/articles")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        // TODO: server error exception?
        Article article = articleService.createArticle(articleDTO).orElseThrow();
        return ResponseEntity.ok(article);
    }

    @PutMapping("/articles/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") int id,
                                                 @Valid @RequestBody ArticleDTO articleDTO) {
        // TODO: server error exception?
        Article article = articleService.updateArticle(id, articleDTO).orElseThrow();
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/articles/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") int id) {
        // JPA Repository throws EmptyResultDataAccessException if no such id
        articleService.deleteArticle(id);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/articles/soft-delete/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> softDeleteArticle(@PathVariable("id") int id) {
        Article article = articleService.softDeleteArticle(id).orElseThrow();
        return ResponseEntity.ok(article);
    }

    @GetMapping("/articles/active/{id}")
    public ResponseEntity<Article> getArticleByIdAndActive(@PathVariable("id") int id) {
        Article article = articleService.findByIdAndState(id, ArticleState.ENABLED).orElseThrow(
            () -> new NoSuchEntityException("No such active article with id: " + id));
        return ResponseEntity.ok(article);
    }

    @GetMapping("/articles/active")
    public ResponseEntity<PagedArticlesDTO> getActiveArticles(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        PagedArticlesDTO pagedArticlesDTO = articleService.findAllByState(title, page, size, sort, ArticleState.ENABLED)
            .orElseThrow(() -> new NoSuchEntityException("No such active articles"));
        return ResponseEntity.ok(pagedArticlesDTO);
    }
}
