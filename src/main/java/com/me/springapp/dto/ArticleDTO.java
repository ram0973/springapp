package com.me.springapp.dto;

import com.me.springapp.model.ArticleTag;
import com.me.springapp.model.ModelState;
import lombok.Builder;

import java.util.Set;

public record ArticleDTO(
    ModelState modelState,
    String dateCreated,
    String title,
    String excerpt,
    String content,
    String image,
    boolean active,
    Set<ArticleTag> tags
) {
    @Builder
    public ArticleDTO {
    }
}
