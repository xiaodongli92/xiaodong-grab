package com.xiaodong.gov.grab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaodong.gov.grab.model.InitData;
import com.xiaodong.gov.grab.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * Created by xiaodong on 2017/4/9.
 * 法院信息抓取
 */
public class CourtMain {

    private static final Logger LOG = LoggerFactory.getLogger(CourtMain.class);

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
//        request();
        detail("fa050ae7-6747-4aec-8c29-786ce43a3f0d", "0", "0");
    }

    public static List<InitData> request(String key, String index, String page) throws IOException,
            ParserConfigurationException, SAXException {
        List<InitData> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("Param", "全文检索:"+key);
        map.put("Index", index);
        map.put("Page", page);
        map.put("Order", "法院层级");
        map.put("Direction", "asc");
        String result = HttpUtil.post("http://wenshu.court.gov.cn/List/ListContent", map);
        result = result.substring(1, result.length() - 1).replaceAll("\\\\\"", "\"");
        JSONArray array = JSON.parseArray(result);
        for (Object o:array) {
            JSONObject jsonObject = (JSONObject) o;
            String id = jsonObject.getString("文书ID");
            InitData data = detail(id, index, page);
            if (null != data) {
                list.add(data);
            }
        }
        return list;
    }

    private static InitData detail(String id, String index, String page) throws ParserConfigurationException, IOException, SAXException {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        String url = "http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx?DocID=" + id;
        String result = HttpUtil.get(url);
        LOG.info("id={}---url={}  ---result={}", id, url, result);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        String[] results = result.split("\n");
        String content = null;
        for (String one:results) {
            String oneTrim = one.trim();
            if (StringUtils.isNotBlank(oneTrim) && oneTrim.startsWith("var jsonHtmlData =")) {
                content = one.trim();
                break;
            }
        }
        if (StringUtils.isBlank(content)) {
            System.exit(1);
            return null;
        }
        content = content.substring(content.indexOf("\"{"), content.length() - 2);
        content = content.substring(1, content.length()).replaceAll("\\\\\"", "\"").replaceAll("\\\\", "");
        System.out.println(content);
        JSONObject jsonObject = JSON.parseObject(content);
        String html = jsonObject.getString("Html");
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div");
        JSONArray array = new JSONArray();
        for (Element element:elements) {
            array.add(element.html());
        }
        LOG.info("解析到id={}, index={}, page={}", id, index, page);
        InitData data = new InitData();
        data.setDocId(id);
        data.setContent(array.toJSONString());
        data.setCreateTime(new Date());
        data.setUrl(url);
        return data;
    }

    private static void content(String content) {

    }
}
