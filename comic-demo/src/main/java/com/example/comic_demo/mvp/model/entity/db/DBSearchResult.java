package com.example.comic_demo.mvp.model.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: 小川
 * Date: 2019/9/5
 * Description:
 */
@Entity
public class DBSearchResult  {
    @Id(autoincrement = true)
    protected Long id;
    @NotNull
    protected String title;
    @NotNull
    protected Long search_time;
    @Generated(hash = 990650882)
    public DBSearchResult(Long id, @NotNull String title,
            @NotNull Long search_time) {
        this.id = id;
        this.title = title;
        this.search_time = search_time;
    }
    @Generated(hash = 567965572)
    public DBSearchResult() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getSearch_time() {
        return this.search_time;
    }
    public void setSearch_time(Long search_time) {
        this.search_time = search_time;
    }
}