package com.me.springapp.dto;

import com.me.springapp.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper( ArticleMapper.class );

    @Mapping(target = "")
    ArticleDTO articleToArticleDto(Article article);
}
