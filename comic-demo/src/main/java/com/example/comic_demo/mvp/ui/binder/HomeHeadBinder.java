package com.example.comic_demo.mvp.ui.binder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.comic_demo.R;
import com.example.comic_demo.app.utils.GlideImageLoader;
import com.example.comic_demo.mvp.model.entity.BannerItem;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.ui.activity.CategoryActivity;
import com.example.comic_demo.mvp.ui.activity.ComicDetailActivity;
import com.example.comic_demo.mvp.ui.activity.RankActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.ItemViewBinder;

/**
 * author: 小川
 * Date: 2019/8/16
 * Description:
 */
public class HomeHeadBinder extends ItemViewBinder<BannerItem, HomeHeadBinder.Holder> {


    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_home_head, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull BannerItem item) {
        Context context = holder.mBanner.getContext();
        //设置图片加载器
        holder.mBanner.setImageLoader(new GlideImageLoader());
        holder.mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < item.getComics().size(); i++) {
            Comic comic = item.getComics().get(i);
            images.add(comic.getCover());
        }
        //设置图片集合
        holder.mBanner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        holder.mBanner.start();
        holder.mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String id = item.getComics().get(position).getId() + "";
                String title = item.getComics().get(position).getTitle() + "";
                ComicDetailActivity.launchActivity((Activity) context, id, title);
            }
        });
        holder.ll_category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RankActivity.class);
                context.startActivity(intent);
            }
        });
        holder.ll_category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryActivity.launchActivity(v.getContext(),"",0);
            }
        });
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.B_banner)
        Banner mBanner;
        @BindView(R.id.ll_category1)
        LinearLayout ll_category1;
        @BindView(R.id.ll_category2)
        LinearLayout ll_category2;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
