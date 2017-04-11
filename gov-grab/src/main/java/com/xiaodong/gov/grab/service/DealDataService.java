package com.xiaodong.gov.grab.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xiaodong.gov.grab.dao.InitDataRepository;
import com.xiaodong.gov.grab.model.DealData;
import com.xiaodong.gov.grab.model.InitData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiaodong on 2017/4/10.
 */
@Service
public class DealDataService {

    private static final Logger LOG = LoggerFactory.getLogger(DealDataService.class);

    @Autowired
    private InitDataRepository initDataRepository;

    public void deal() {
        long maxId = initDataRepository.maxId();
        for (long i=1; i<maxId; i++) {
            InitData initData = initDataRepository.findOne(i);
            if (null == initData) {
                continue;
            }
            LOG.info("url = {}", initData.getUrl());
            String name = dealData(initData);
            LOG.info("name = {}", name);
            LOG.info("************************************************************");
        }
    }

    private static String dealData(InitData initData) {
        String content = initData.getContent();
        DealData dealData = new DealData();
        dealData.setUrl(initData.getUrl());
        dealData.setContent(content);
        JSONArray array = JSON.parseArray(content);
        StringBuilder builder = new StringBuilder();
        for (Object o:array) {
            String line = (String) o;
            if (line.startsWith("被告人") || line.startsWith("罪犯") || line.startsWith("上诉人（原审被告人）")) {
                String[] field = line.split("，");
                String name = field[0].replaceAll("上诉人（原审被告人）", "")
                        .replaceAll("被告人", "")
                        .replaceAll("罪犯", "")
                        ;
                if (name.length() > 4) {
                    continue;
                }
                builder.append(name).append(",");
            }
        }
        return builder.toString();
    }
}
