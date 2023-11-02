package com.absolute.wemedia.service.impl;

import com.absolute.wemedia.mapper.WmNewsMapper;
import com.absolute.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WmAutoScanServiceImpl implements WmNewsAutoScanService {
    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Override
    public void autoScanWmNews(Integer id) {

    }
}
