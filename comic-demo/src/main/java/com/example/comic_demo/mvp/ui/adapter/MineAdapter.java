package com.example.comic_demo.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.comic_demo.R;
import com.example.comic_demo.mvp.model.entity.MineTitle;
import com.example.comic_demo.mvp.ui.holder.MineHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/9/14
 * Description:
 */
public class MineAdapter extends DefaultAdapter<MineTitle> {
    public MineAdapter(List<MineTitle> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<MineTitle> getHolder(@NonNull View v, int viewType) {
        return new MineHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_mine;
    }
}
