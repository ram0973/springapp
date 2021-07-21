package com.me.springapp.dto;

import com.me.springapp.model.Article;

import java.util.List;

public record PagedArticlesDTO(List<Article> articles, int currentPage, long totalItems, int totalPages) {
}


