package com.absolute.wemedia.service;

import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.wemedia.dtos.WmNewsPageReqDto;
import com.absolute.model.wemedia.pojos.WmNews;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WmNewsService extends IService<WmNews> {
    public ResponseResult findAll(WmNewsPageReqDto dto);
}
