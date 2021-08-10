package com.me.springapp.dto;

import com.me.springapp.model.ArticleTag;
import com.me.springapp.model.User;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleDTO(
    int id,
    String title,
    String excerpt,
    String content,
    String image,
    boolean active,
    LocalDateTime dateCreated,
    User User,
    Set<ArticleTag> tags
) {
}
