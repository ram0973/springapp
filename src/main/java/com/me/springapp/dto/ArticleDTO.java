package com.me.springapp.dto;

import com.me.springapp.model.ArticleTag;
import lombok.Builder;

import java.util.Set;

public record ArticleDTO(
    String title,
    String excerpt,
    String content,
    String image,
    boolean active,
    String dateCreated,
    Set<ArticleTag> tags
) {
    @Builder
    public ArticleDTO {
    }
}
