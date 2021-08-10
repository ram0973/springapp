package com.me.springapp.dto;

import com.me.springapp.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(source = "id", target = "id")
    ArticleDTO articleToDto(Article article);

    Article articleFromDto(ArticleDTO articleDTO);

    @Mapping(target = "id", ignore = true)
    void updateArticleFromDto(ArticleDTO articleDTO, @MappingTarget Article article);
}
