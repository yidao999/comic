package com.example.comic_demo.mvp.model.entity.db;

import com.example.comic_demo.db.util.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: 小川
 * Date: 2019/9/5
 * Description:
 */
@Entity
public class DBChapters {
    @Id
    protected Long id;
    //漫画ID
    protected Long comic_id;
    //漫画标题
    protected String title;
    //章节标题
    protected String chapters_title;
    //章节编号
    protected int chapters;
    //章节漫画页面
    protected int num;
    //当前下载的漫画页面
    protected int current_num;
    //开始下载时间
    protected Long create_time;
    //最后更新时间
    protected Long update_time;
    /*state状态数据库保存*/
    protected int stateInte;
    @Convert(columnType = String.class, converter = StringConverter.class)
    protected List<String> comiclist;
    @Convert(columnType = String.class, converter = StringConverter.class)
    protected List<String> chapters_path;
    @Generated(hash = 232957805)
    public DBChapters(Long id, Long comic_id, String title, String chapters_title,
            int chapters, int num, int current_num, Long create_time,
            Long update_time, int stateInte, List<String> comiclist,
            List<String> chapters_path) {
        this.id = id;
        this.comic_id = comic_id;
        this.title = title;
        this.chapters_title = chapters_title;
        this.chapters = chapters;
        this.num = num;
        this.current_num = current_num;
        this.create_time = create_time;
        this.update_time = update_time;
        this.stateInte = stateInte;
        this.comiclist = comiclist;
        this.chapters_path = chapters_path;
    }
    @Generated(hash = 405250226)
    public DBChapters() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getComic_id() {
        return this.comic_id;
    }
    public void setComic_id(Long comic_id) {
        this.comic_id = comic_id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getChapters_title() {
        return this.chapters_title;
    }
    public void setChapters_title(String chapters_title) {
        this.chapters_title = chapters_title;
    }
    public int getChapters() {
        return this.chapters;
    }
    public void setChapters(int chapters) {
        this.chapters = chapters;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getCurrent_num() {
        return this.current_num;
    }
    public void setCurrent_num(int current_num) {
        this.current_num = current_num;
    }
    public Long getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }
    public Long getUpdate_time() {
        return this.update_time;
    }
    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }
    public int getStateInte() {
        return this.stateInte;
    }
    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }
    public List<String> getComiclist() {
        return this.comiclist;
    }
    public void setComiclist(List<String> comiclist) {
        this.comiclist = comiclist;
    }
    public List<String> getChapters_path() {
        return this.chapters_path;
    }
    public void setChapters_path(List<String> chapters_path) {
        this.chapters_path = chapters_path;
    }
}