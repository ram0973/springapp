package com.me.springapp.controller;

import com.me.springapp.dto.ArticleDTO;
import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.model.Article;
import com.me.springapp.model.ModelState;
import com.me.springapp.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: check this
//@CrossOrigin(origins = "*")
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
        return articleService.findAll(title, page, size, sort);
    }

    @GetMapping("/articles/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") int id) {
        return articleService.findById(id);
    }

    @PostMapping("/articles")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.createArticle(articleDTO);
    }

    @PutMapping("/articles/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") int id, @RequestBody ArticleDTO articleDTO) {
        return articleService.updateArticle(id, articleDTO);
    }

    @DeleteMapping("/articles/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") int id) {
        return articleService.deleteArticle(id);
    }

    @DeleteMapping("/articles/soft-delete/{id}")
    //@PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> softDeleteArticle(@PathVariable("id") int id) {
        return articleService.softDeleteArticle(id);
    }

    @GetMapping("/articles/active/{id}")
    public ResponseEntity<Article> getArticleByIdAndActive(@PathVariable("id") int id) {
        return articleService.findByIdAndState(id, ModelState.ENABLED);
    }

    @GetMapping("/articles/active")
    public ResponseEntity<PagedArticlesDTO> getActiveArticles(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        return articleService.findAllByState(title, page, size, sort, ModelState.ENABLED);
    }
}
