package com.xiaodong.gov.grab.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xiaodong on 2017/4/9.
 * 原始数据
 */
@Entity
@Table(name = "init_data")
public class InitData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String docId;

    private String url;

    private String content;

    private Date createTime;

    @Override
    public String toString() {
        return "InitData{" +
                "id=" + id +
                ", docId='" + docId + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
