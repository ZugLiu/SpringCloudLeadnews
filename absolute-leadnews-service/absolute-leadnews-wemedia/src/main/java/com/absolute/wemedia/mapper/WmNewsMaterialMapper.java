package com.absolute.wemedia.mapper;

import com.absolute.model.wemedia.pojos.WmNewsMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {
    void saveRelations(@Param("materialIds")List<Integer> materialIds, @Param("newsId") Integer newsId, @Param("type") Short type);
}
