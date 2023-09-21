package com.absolute.article.service.impl;

import com.absolute.article.mapper.ApArticleMapper;
import com.absolute.article.service.ApArticleService;
import com.absolute.model.article.dtos.ArticleHomeDto;
import com.absolute.model.article.pojos.ApArticle;
import com.absolute.model.common.constants.ArticleConstants;
import com.absolute.model.common.dtos.ResponseResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {
    // 单页加载最大数
    public static final short MAX_PAGE_SIZE = 50;

    @Autowired
    ApArticleMapper apArticleMapper;

    @Override
    public ResponseResult load(Short loadType, ArticleHomeDto dto) {
        // 1.参数校验
        Integer size = dto.getSize();
        if(size == null || size == 0) {
            size = 10; // 默认一页大小是10
        }
        size = Math.min(size, MAX_PAGE_SIZE);
        dto.setSize(size);

        // 类型参数校验
        if(!loadType.equals(ArticleConstants.LOADTYPE_LOAD_MORE) &&
           !loadType.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            loadType = ArticleConstants.LOADTYPE_LOAD_MORE;
        }

        // 文章频道校验
        if(StringUtils.isEmpty(dto.getTag())) {
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        // 时间校验
        if(dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if(dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());

        // 2.查询数据
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(dto, loadType);

        // 3.结果封装
        ResponseResult responseResult = ResponseResult.okResult(apArticles);

        return responseResult;
    }
}
