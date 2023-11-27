package com.me.springapp.dto;

import com.me.springapp.model.ArticleState;
import com.me.springapp.model.ArticleTag;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record ArticleDTO(
    @Enumerated(EnumType.STRING) ArticleState state,
    @DateTimeFormat String dateCreated,
    @NotNull @NotBlank String title,
    @NotNull String excerpt,
    @NotNull @NotBlank String content,
    String image,
    Set<ArticleTag> tags
) {
    @Builder
    public ArticleDTO {
    }
}
