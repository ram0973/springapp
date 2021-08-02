package com.me.springapp.controller;

import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.model.Article;
import com.me.springapp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ArticleController {
    private ArticleService service;

    @Autowired
    public void setArticleService(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/articles")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<PagedArticlesDTO> getArticles(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        return service.findAll(title, page, size, sort);
    }

    @GetMapping("/articles/{id}")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping("/articles")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        return service.createArticle(article);
    }

    @PutMapping("/articles/{id}")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") int id, @RequestBody Article Article) {
        return service.updateArticle(id, Article);
    }

    @DeleteMapping("/articles/{id}")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") int id) {
        return service.deleteArticle(id);
    }

    @GetMapping("/articles/active/{id}")
    public ResponseEntity<Article> getArticleByIdAndActive(@PathVariable("id") int id) {
        return service.findByIdAndActive(id);
    }

    @GetMapping("/articles/active")
    public ResponseEntity<PagedArticlesDTO> getActiveArticles(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        return service.findAllByActive(title, page, size, sort);
    }
}
