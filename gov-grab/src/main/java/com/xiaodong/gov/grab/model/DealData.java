package com.xiaodong.gov.grab.model;

import javax.persistence.*;

/**
 * Created by xiaodong on 2017/4/10.
 * 处理数据
 */
@Entity
@Table(name = "deal_data")
public class DealData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String url;

    private String content;

    private String dealContent;

    @Override
    public String toString() {
        return "DealData{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", dealContent='" + dealContent + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }
}
