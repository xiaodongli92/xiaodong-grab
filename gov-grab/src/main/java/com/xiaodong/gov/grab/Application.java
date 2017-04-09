package com.xiaodong.gov.grab;

import com.xiaodong.gov.grab.service.InitDataService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiaodong on 2017/4/9.
 */
@RestController
@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    private InitDataService initDataService;

    @RequestMapping("/run")
    @ResponseBody
    public String run(String key) {
        try {
            if (StringUtils.isBlank(key)) {
                return "key must be not null";
            }
            initDataService.executeInitData(key);
            return "ok";
        } catch (Exception e) {
            LOG.error("", e);
            return "error:" + e.getMessage();
        }
    }

    @RequestMapping("/check")
    @ResponseBody
    public String run() {
        try {
            return "ok";
        } catch (Exception e) {
            LOG.error("", e);
            return "error:" + e.getMessage();
        }
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}
