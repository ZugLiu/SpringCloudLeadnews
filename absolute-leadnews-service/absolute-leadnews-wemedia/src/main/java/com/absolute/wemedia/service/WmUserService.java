package com.absolute.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.wemedia.dtos.WmLoginDto;
import com.absolute.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {

    /**
     * 自媒体端登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmLoginDto dto);

}