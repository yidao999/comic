package com.example.comic_demo.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.comic_demo.R;
import com.example.comic_demo.mvp.ui.holder.DetailCatalogHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/9/5
 * Description:
 */
public class DetailCatalogAdapter extends DefaultAdapter<String> {

    public DetailCatalogAdapter(List<String> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<String> getHolder(@NonNull View v, int viewType) {
        return new DetailCatalogHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.detail_catalog_item;
    }
}
