package com.xiaodong.gov.grab.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: http公用方法
 * @Author: xiaodongli92@gmail.com
 * @CreateDate: 2016/6/7
 */
public class HttpUtil {

    private static final int MAX_TOTAL = 100;
    private static final int DEFAULT_MAX_PER_ROUTE = 200;
    private static final int CONNECT_TIME_OUT = 60000;
    private static final int SOCKET_TIME_OUT = 60000;

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager http = new PoolingHttpClientConnectionManager();
        http.setMaxTotal(MAX_TOTAL);
        http.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT)
                .setSocketTimeout(SOCKET_TIME_OUT).build();
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(http).build();
    }

    private HttpUtil(){

    }

    /**
     * post json 请求
     */
    public static String postJson(final String url, final String jsonStr) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type", "application/json");
        setBrowser(post);
        HttpEntity httpEntity = new StringEntity(jsonStr, "UTF-8");
        post.setEntity(httpEntity);
        HttpResponse response = httpClient.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * post 请求
     */
    public static String post(final String url, final Map<String,String> paramMap) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        setBrowser(post);
        if (null != paramMap && !paramMap.isEmpty()) {
            List<NameValuePair> params = getNameValuePair(paramMap);
            post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        }
        HttpResponse response = httpClient.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * get 请求
     * 参数传递
     */
    public static String get(final String url, final Map<String, String> paramMap) {
        String queryUrl = url + buildQuery(paramMap);
        return get(queryUrl);
    }

    /**
     * get 请求
     */
    public static String get(String url){
        try {
            HttpGet get = new HttpGet(url);
            setBrowser(get);
            HttpResponse response = httpClient.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setBrowser(HttpRequestBase requestBase) {
        requestBase.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        requestBase.addHeader("Cookie", "FSSBBIl1UgzbN7N80S=vEkKWwQOHZmnEO1.CmeuMdpxnzU9aVFoRf9dra8fIKAnzq0h4CeGZxzDfiEORBLt; ASP.NET_SessionId=jykq2oenav5hksxa44lsdc0q; _gscu_2116842793=91704863nlb2c420; _gscbrs_2116842793=1; Hm_lvt_3f1a54c5a86d62407544d433f6418ef5=1491704863; Hm_lpvt_3f1a54c5a86d62407544d433f6418ef5=1491716907; wzwsconfirm=8560254cc0f612e6491c9e4aa03b26a6; wzwstemplate=MQ==; ccpassport=36a3d24d7f8d67eb811021e4ee7477cb; wzwschallenge=-1; wzwsvtime=1491722286; wafenterurl=L0NyZWF0ZUNvbnRlbnRKUy9DcmVhdGVDb250ZW50SlMuYXNweD9Eb2NJRD04NTJhZjI1YS1hYTNjLTQxZmMtYmU0Yy1kNTcwYzYzOTljY2Y=; __utmt=1; __utma=61363882.1885714999.1491724899.1491724899.1491724899.1; __utmb=61363882.2.10.1491724899; __utmc=61363882; __utmz=61363882.1491724899.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); wafcookie=aa220c250464a23aa4049c5f18a647e0; wafverify=63c441ef1b0c52e83e7238075938206b; FSSBBIl1UgzbN7N80T=1m2Iy.M4tG5Q8mSaaVojSqFpx2xAMynQcFfjNjEz6q5mtCX89JJpvSNE11Kk06YDPU_DSHkFfnxsalRdt5c5dl4lJ9HErR66XxFgjQSvQvlSA2dwmxNkBsJwPE4PVmXC9vREuhDdNstodoZsAxepLS5IWkydNOpZnEB0KIxBO7NDxKfROrTG__.tesZ5ENPbAhTRTlZ64gOYrYh8lwBQNFYjv9dKpCaKjEg9pc.OtC_TP9vioMTxn69nKaXmUBZabocwUXaEQI1k89OZKCM_e2RefqwNiZItROuJpfa.oHy_CgCbLAceNIg0E6NTcRpGToG");
    }

    /**
     * 参数转换
     * Map<String,String> ==》List<NameValuePair>
     */
    private static List<NameValuePair> getNameValuePair(final Map<String,String> paramMap) {
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String,String> entry:paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        return params;
    }

    private static String buildQuery(final Map<String,String> params) {
        if (null == params || params.isEmpty()) {
            return "";
        }
        StringBuilder query = new StringBuilder();
        boolean hasParam = false;
        Set<Map.Entry<String,String>> entries = params.entrySet();
        for (Map.Entry<String,String> entry:entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNoneBlank(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(value);
            }
        }
        return query.toString();
    }
}
