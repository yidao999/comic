package com.example.comic_demo.mvp.ui.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * author: 小川
 * Date: 2019/9/11
 * Description:
 */
public class NoScrollGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
