package com.me.springapp.dto;

import com.me.springapp.model.Article;

import java.util.List;

public class PagedArticlesDTO {
    List<Article> articles;
    int currentPage;
    long totalItems;
    int totalPages;

    public PagedArticlesDTO(List<Article> articles, int currentPage, long totalItems, int totalPages) {
        this.articles = articles;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
