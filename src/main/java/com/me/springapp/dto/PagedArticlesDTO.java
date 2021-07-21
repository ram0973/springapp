package com.me.springapp.dto;

import com.me.springapp.model.Article;

import java.util.List;

public class PagedArticlesDTO {
    private List<Article> articles;
    int currentPage;
    long totalItems;
    int totalPages;
}


