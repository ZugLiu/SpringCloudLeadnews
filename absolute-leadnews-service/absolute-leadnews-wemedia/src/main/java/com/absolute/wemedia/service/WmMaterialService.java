package com.absolute.wemedia.service;

import com.absolute.model.common.dtos.ResponseResult;
import com.absolute.model.wemedia.pojos.WmMaterial;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {
    public ResponseResult uploadPicture(MultipartFile multipartFile);
}
