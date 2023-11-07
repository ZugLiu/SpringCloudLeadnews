package com.absolute.wemedia.service.impl;

import com.absolute.common.aliyun.GreenTextScan2023;
import com.absolute.file.service.FileStorageService;
import com.absolute.model.wemedia.pojos.WmNews;
import com.absolute.wemedia.mapper.WmNewsMapper;
import com.absolute.wemedia.service.WmNewsAutoScanService;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {
    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Override
    public void autoScanWmNews(Integer id) {
        //1. 查询自媒体文章
        WmNews wmNews = wmNewsMapper.selectById(id);
        if(wmNews == null) {
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章不存在");
        }

        if(wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())) {
            // 从内容中提取纯文本内容和图片
            Map<String, Object> textAndImages = handleTextAndImages(wmNews);

            // 2. 审核文本内容 用阿里云接口
            boolean isTextSuccessful = handleTextScan((String)textAndImages.get("content"), wmNews);

            // 3. 审核图片内容 用阿里云接口
            boolean isImageSuccessful = handleImageScan((List<String>)textAndImages.get("images"), wmNews);
        }
    }

    @Autowired
    GreenTextScan2023 greenTextScan2023;

    /**
     * 阿里云接口审核文本
     * @param content
     * @param wmNews
     * @return
     */
    private boolean handleTextScan(String content, WmNews wmNews) {
        boolean flag = true;

        if((wmNews.getTitle()+""+wmNews.getContent()).length() == 0) {
            return flag;
        }

        try {
            Map scanRes = greenTextScan2023.textScan(content);
            if(scanRes != null) {
                if(StringUtils.isNotBlank((String)scanRes.get("labels"))){
                    flag = false;
                    updateWmNews(wmNews, (short)2, "当前文章中存在违规内容");
                }
            }
        } catch (Exception e){
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    @Autowired
    FileStorageService fileStorageService;

    private boolean handleImageScan(List<String> images, WmNews wmNews) {
        boolean flag = true;

        if(images == null || images.size() == 0) {
            return flag;
        }

        // 从minIO下载图片
        // 图片去重
        images = images.stream().distinct().collect(Collectors.toList());

        ArrayList<byte[]> imageList = new ArrayList<>();

        for(String image : images) {
            byte[] bytes = fileStorageService.downLoadFile(image);
            imageList.add(bytes);
        }

        // 审核图片
        try {

        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 修改文章状态
     * @param wmNews
     * @param status
     * @param reason
     */
    private void updateWmNews(WmNews wmNews, short status, String reason) {
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }
    /**
     * 提取WmNews中的文本和图片
     * @param wmNews
     * @return
     */
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {
        // 存储文本内容
        StringBuilder stringBuilder = new StringBuilder();
        // 存储图片
        ArrayList<String> images = new ArrayList<>();

        // 提取正文中的的文本和图片
        if(StringUtils.isNotBlank(wmNews.getContent())) {
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for(Map map : maps) {
                if(map.get("type").equals("text")) {
                    stringBuilder.append(map.get("value"));
                }

                if(map.get("type").equals("image")) {
                    images.add((String) map.get("value"));
                }
            }
        }

        // 提取封面图
        if(StringUtils.isNotBlank(wmNews.getImages())) {
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("content", stringBuilder.toString());
        resultMap.put("images", images);
        return resultMap;
    }
}
