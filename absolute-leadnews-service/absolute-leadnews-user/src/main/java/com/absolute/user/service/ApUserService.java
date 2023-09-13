package com.absolute.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.user.dtos.LoginDto;
import com.absolute.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {
    /**
     * app端登录功能
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
