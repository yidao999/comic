package com.example.comic_demo.mvp.model.entity;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/8/16
 * Description:
 */
public class BannerItem {
    List<Comic> comics;

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
}
