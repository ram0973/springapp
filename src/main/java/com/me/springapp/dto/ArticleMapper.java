package com.me.springapp.dto;

import com.me.springapp.model.Article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleMapper {

    static final String dateTimeFormat = "dd-MM-yyyy HH:mm";

    public static ArticleDTO articleToDto(Article article) {
        return ArticleDTO.builder()
            .title(article.getTitle())
            .excerpt(article.getExcerpt())
            .content(article.getContent())
            .image(article.getImage())
            .active(article.isActive())
            .dateCreated(article.getDateCreated().format(DateTimeFormatter.ofPattern(dateTimeFormat)))
            .tags(article.getTags())
            .build();
    }

    public static Article articleFromDto(ArticleDTO articleDTO) {
        return Article.builder()
            .title(articleDTO.title())
            .excerpt(articleDTO.excerpt())
            .content(articleDTO.content())
            .image(articleDTO.image())
            .active(articleDTO.active())
            .dateCreated(LocalDateTime.parse(articleDTO.dateCreated(), DateTimeFormatter.ofPattern(dateTimeFormat)))
            .tags(articleDTO.tags())
            .build();
    }

    public static void updateArticleFromDto(Article article, ArticleDTO articleDTO) {
        article.setTitle(articleDTO.title());
        article.setExcerpt(articleDTO.excerpt());
        article.setContent(articleDTO.content());
        article.setImage(articleDTO.image());
        article.setActive(articleDTO.active());
        article.setDateCreated(LocalDateTime.parse(articleDTO.dateCreated(), DateTimeFormatter.ofPattern(dateTimeFormat)));
        article.setTags(articleDTO.tags());
    }
}
