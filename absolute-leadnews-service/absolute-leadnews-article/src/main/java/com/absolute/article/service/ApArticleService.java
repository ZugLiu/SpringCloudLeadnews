package com.absolute.article.service;

import com.absolute.model.article.dtos.ArticleHomeDto;
import com.absolute.model.article.pojos.ApArticle;
import com.absolute.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * 根据loadType加载文章列表
     * @param loadType
     * @param dto
     * @return
     */
    ResponseResult load(Short loadType, ArticleHomeDto dto);
}
