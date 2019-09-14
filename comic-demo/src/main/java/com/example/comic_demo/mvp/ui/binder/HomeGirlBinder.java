package com.example.comic_demo.mvp.ui.binder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.comic_demo.R;
import com.example.comic_demo.mvp.model.entity.BoyHomeItem;
import com.example.comic_demo.mvp.model.entity.GirlHomeItem;
import com.example.comic_demo.mvp.ui.activity.ComicDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/8/16
 * Description:
 */
public class HomeGirlBinder extends ItemViewBinder<GirlHomeItem, HomeGirlBinder.Holder> {


    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_homepage_three, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull GirlHomeItem item) {
        Context context = holder.title.getContext();
        holder.title.setText(item.getTitle());
        holder.describe.setText(item.getDescribe());
        Glide.with(context).load(item.getCover()).into(holder.imageView);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getId() + "";
                String title = item.getTitle() + "";
                ComicDetailActivity.launchActivity((Activity) context, id, title);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getId() + "";
                String title = item.getTitle() + "";
                ComicDetailActivity.launchActivity((Activity) context, id, title);
            }
        });
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_describe)
        TextView describe;
        @BindView(R.id.iv_image)
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
