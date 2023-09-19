package com.absolute.model.article.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleHomeDto {
    // 最大时间
    Date maxBeHotTime;
    // 最小时间
    Date minBeHotTime;
    // 分页size
    Integer size;
    // 频道ID
    String tag;
}
