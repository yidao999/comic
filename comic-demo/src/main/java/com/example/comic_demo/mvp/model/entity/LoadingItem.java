package com.example.comic_demo.mvp.model.entity;

/**
 * author: 小川
 * Date: 2019/9/11
 * Description:
 */
public class LoadingItem extends Comic{
    boolean isLoading;
    public LoadingItem(boolean isLoading){
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
