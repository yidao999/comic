package com.example.comic_demo.mvp.model.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: 小川
 * Date: 2019/9/5
 * Description:
 */
@Entity
public class DownInfo  {

    @Id
    protected long id;
    /*漫画ID*/
    protected long comic_id;
    /*存储位置*/
    protected String savePath;
    /*文件总长度*/
    protected long countLength;
    /*下载长度*/
    protected long readLength;
    /*下载唯一的HttpService*/
//    @Transient
//    protected ComicService service;
    /*回调监听*/
//    @Transient
//    protected HttpDownOnNextListener listener;
    /*超时设置*/
    protected int connectonTime = 6;
    /*state状态数据库保存*/
    protected int stateInte;
    /*url*/
    private String url;
    @Generated(hash = 588015662)
    public DownInfo(long id, long comic_id, String savePath, long countLength,
            long readLength, int connectonTime, int stateInte, String url) {
        this.id = id;
        this.comic_id = comic_id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
    }
    @Generated(hash = 928324469)
    public DownInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getComic_id() {
        return this.comic_id;
    }
    public void setComic_id(long comic_id) {
        this.comic_id = comic_id;
    }
    public String getSavePath() {
        return this.savePath;
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    public long getCountLength() {
        return this.countLength;
    }
    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }
    public long getReadLength() {
        return this.readLength;
    }
    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }
    public int getConnectonTime() {
        return this.connectonTime;
    }
    public void setConnectonTime(int connectonTime) {
        this.connectonTime = connectonTime;
    }
    public int getStateInte() {
        return this.stateInte;
    }
    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}