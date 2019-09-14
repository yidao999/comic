package com.example.comic_demo.mvp.ui.holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comic_demo.R;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

/**
 * author: 小川
 * Date: 2019/9/5
 * Description:
 */
public class DetailCatalogHolder extends BaseHolder<String> {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.iv_location)
    ImageView mLocation;

    public DetailCatalogHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull String data, int position) {
        mTitle.setText(data);
        if (position == 0) {
            mTitle.setTextColor(ContextCompat.getColor(mTitle.getContext(), R.color.colorPrimary));
            mLocation.setVisibility(View.VISIBLE);
        }
    }
}
