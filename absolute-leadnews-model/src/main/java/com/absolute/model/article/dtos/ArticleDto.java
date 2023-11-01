package com.absolute.model.article.dtos;

import com.absolute.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {
    private String content;
}
