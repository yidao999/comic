package com.example.comic_demo.mvp.model.entity;

import com.example.comic_demo.db.util.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: 小川
 * Date: 2019/8/15
 * Description:
 */

@Entity
public class Comic {
    @Id
    protected long id;
    //标题
    protected String title;
    //封面图片
    protected String cover;
    //作者
    protected String author;
    //章节标题
    @Convert(columnType = String.class, converter = StringConverter.class)
    protected List<String> chapters;

    @Convert(columnType = String.class, converter = StringConverter.class)
    protected List<String> chapters_url;

    //标签
    @Convert(columnType = String.class, converter = StringConverter.class)
    protected List<String> tags;
    //收藏数
    protected String collections;
    //详情
    protected String describe;
    //评分
    protected String point;
    //人气值
    protected String popularity;
    //话题量
    protected String topics;
    //更新时间
    protected String updates;
    //状态
    protected String status;
    //默认阅读方式
    protected int readType;
    //当前章节
    protected int currentChapter;
    //收藏时间
    protected long collectTime;
    //点击时间
    protected long clickTime;
    //下载时间
    protected long downloadTime;
    //是否收藏
    protected boolean isCollected;

    /*state状态数据库保存*/
    protected int stateInte;
    //当前页
    protected int current_page;
    //当前共有多少页面
    protected int current_page_all;
    //有多少话在下载
    protected int download_num;
    //下载完成多少话
    protected int download_num_finish;
    //来自什么资源
    protected int from;

    public DownState getState() {
        switch (getStateInte()){
            case 0:
                return DownState.START;
            case 1:
                return DownState.DOWN;
            case 2:
                return DownState.PAUSE;
            case 3:
                return DownState.STOP;
            case 4:
                return DownState.ERROR;
            case 5:
                return DownState.FINISH;
            default:
                return DownState.FINISH;
        }
    }

    public void setState(DownState state) {
        setStateInte(state.getState());
    }

    @Generated(hash = 1962665814)
    public Comic(long id, String title, String cover, String author,
            List<String> chapters, List<String> chapters_url, List<String> tags,
            String collections, String describe, String point, String popularity,
            String topics, String updates, String status, int readType,
            int currentChapter, long collectTime, long clickTime, long downloadTime,
            boolean isCollected, int stateInte, int current_page,
            int current_page_all, int download_num, int download_num_finish,
            int from) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.chapters = chapters;
        this.chapters_url = chapters_url;
        this.tags = tags;
        this.collections = collections;
        this.describe = describe;
        this.point = point;
        this.popularity = popularity;
        this.topics = topics;
        this.updates = updates;
        this.status = status;
        this.readType = readType;
        this.currentChapter = currentChapter;
        this.collectTime = collectTime;
        this.clickTime = clickTime;
        this.downloadTime = downloadTime;
        this.isCollected = isCollected;
        this.stateInte = stateInte;
        this.current_page = current_page;
        this.current_page_all = current_page_all;
        this.download_num = download_num;
        this.download_num_finish = download_num_finish;
        this.from = from;
    }

    @Generated(hash = 1347984162)
    public Comic() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getChapters() {
        return chapters;
    }

    public void setChapters(List<String> chapters) {
        this.chapters = chapters;
    }

    public List<String> getChapters_url() {
        return chapters_url;
    }

    public void setChapters_url(List<String> chapters_url) {
        this.chapters_url = chapters_url;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCollections() {
        return collections;
    }

    public void setCollections(String collections) {
        this.collections = collections;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReadType() {
        return readType;
    }

    public void setReadType(int readType) {
        this.readType = readType;
    }

    public int getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(int currentChapter) {
        this.currentChapter = currentChapter;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public long getClickTime() {
        return clickTime;
    }

    public void setClickTime(long clickTime) {
        this.clickTime = clickTime;
    }

    public long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getCurrent_page_all() {
        return current_page_all;
    }

    public void setCurrent_page_all(int current_page_all) {
        this.current_page_all = current_page_all;
    }

    public int getDownload_num() {
        return download_num;
    }

    public void setDownload_num(int download_num) {
        this.download_num = download_num;
    }

    public int getDownload_num_finish() {
        return download_num_finish;
    }

    public void setDownload_num_finish(int download_num_finish) {
        this.download_num_finish = download_num_finish;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public boolean getIsCollected() {
        return this.isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }
}
