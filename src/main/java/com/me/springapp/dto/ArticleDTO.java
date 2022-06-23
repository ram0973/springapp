package com.me.springapp.dto;

import com.me.springapp.model.ArticleTag;
import com.me.springapp.model.ModelState;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public record ArticleDTO(
    @Enumerated(EnumType.STRING) ModelState modelState,
    @DateTimeFormat String dateCreated,
    @NotNull @NotBlank String title,
    @NotNull String excerpt,
    @NotNull @NotBlank String content,
    String image,
    @NotNull boolean active,
    Set<ArticleTag> tags
) {
    @Builder
    public ArticleDTO {
    }
}
