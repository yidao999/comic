package com.example.comic_demo.mvp.model.entity;

/**
 * author: 小川
 * Date: 2019/8/15
 * Description:
 */
public class HomeTitleEntity extends Comic{
    //标题名字
    public String itemTitle;
    //标题种类
    public int TitleType;

    public HomeTitleEntity(String itemTitle){
        this.itemTitle = itemTitle;
    }

    public HomeTitleEntity(){
    }


    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getTitleType() {
        return TitleType;
    }

    public void setTitleType(int titleType) {
        TitleType = titleType;
    }
}
