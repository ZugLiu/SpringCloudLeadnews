package com.absolute.wemedia.controller.v1;

import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {
    @Autowired
    WmChannelService wmChannelService;

    @GetMapping("/channels")
    public ResponseResult getAllChannels() {
        return wmChannelService.getAllChannels();
    }
}
