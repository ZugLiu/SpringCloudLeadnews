package com.absolute.wemedia.service.impl;

import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.wemedia.pojos.WmChannel;
import com.absolute.wemedia.mapper.WmChannelMapper;
import com.absolute.wemedia.service.WmChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {
    @Override
    public ResponseResult getAllChannels() {
        return ResponseResult.okResult(list());
    }
}
