package com.absolute.wemedia.service;

import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.wemedia.pojos.WmChannel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WmChannelService extends IService<WmChannel> {
    /**
     * get all channels
      * @return
     */
    public ResponseResult getAllChannels();
}
