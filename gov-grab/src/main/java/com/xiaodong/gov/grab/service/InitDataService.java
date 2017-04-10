package com.xiaodong.gov.grab.service;

import com.xiaodong.gov.grab.CourtMain;
import com.xiaodong.gov.grab.dao.InitDataRepository;
import com.xiaodong.gov.grab.model.InitData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Created by xiaodong on 2017/4/9.
 */
@Service
public class InitDataService {

    @Autowired
    private InitDataRepository initDataRepository;

    public void executeInitData(String key) throws ParserConfigurationException, SAXException, IOException {
        for (int i=101; i<=1000; i++) {
            List<InitData> list = CourtMain.request(key, String.valueOf(i), "5");
            initDataRepository.save(list);
        }
    }
}
