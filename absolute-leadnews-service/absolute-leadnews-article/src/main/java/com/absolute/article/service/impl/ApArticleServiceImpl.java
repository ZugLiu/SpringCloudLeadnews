package com.absolute.article.service.impl;

import com.absolute.article.mapper.ApArticleConfigMapper;
import com.absolute.article.mapper.ApArticleContentMapper;
import com.absolute.article.mapper.ApArticleMapper;
import com.absolute.article.service.ApArticleService;
import com.absolute.model.article.dtos.ArticleDto;
import com.absolute.model.article.dtos.ArticleHomeDto;
import com.absolute.model.article.pojos.ApArticle;
import com.absolute.model.article.pojos.ApArticleConfig;
import com.absolute.model.article.pojos.ApArticleContent;
import com.absolute.model.common.constants.ArticleConstants;
import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {
    // 单页加载最大数
    public static final short MAX_PAGE_SIZE = 50;

    @Autowired
    ApArticleMapper apArticleMapper;
    @Autowired
    ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    ApArticleContentMapper apArticleContentMapper;

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

    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        // 1. 检查参数
        if(dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticle apArticle = new ApArticle();
        try {
            BeanUtils.copyProperties(apArticle, dto);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        // 2. 判断是否存在id
        if(dto.getId() == null) {
            // 2.1 不存在id, 保存文章，文章配置，文章内容
            // 保存文章
            save(apArticle);
            // 保存配置
            ApArticleConfig apArticleConfig = new ApArticleConfig(apArticle.getId());
            apArticleConfigMapper.insert(apArticleConfig);
            // 保存文章内容
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);
        }else{
            // 2.2 存在id, 修改文章、文章内容
            // 修改文章
            updateById(apArticle);
            // 修改文章内容
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(
                    ApArticleContent::getArticleId, dto.getId()
            ));
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }
        return ResponseResult.okResult(apArticle.getId());
    }
}
