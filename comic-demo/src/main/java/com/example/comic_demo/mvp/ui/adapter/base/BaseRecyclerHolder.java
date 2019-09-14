package com.example.comic_demo.mvp.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.comic_demo.R;
import com.example.comic_demo.app.utils.DisplayUtil;
import com.example.comic_demo.app.utils.GlideImageLoader;

/**
 * author: 小川
 * Date: 2019/9/11
 * Description: 万能的RecyclerView的ViewHolder
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;

    private BaseRecyclerHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        //指定一个初始为8
        views = new SparseArray<>(8);
    }

    /**
     * 取得一个RecyclerHolder对象
     * @param context 上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static BaseRecyclerHolder getRecyclerHolder(Context context,View itemView){
        return new BaseRecyclerHolder(context,itemView);
    }

    public SparseArray<View> getViews(){
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null ){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public BaseRecyclerHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        if(text==null){
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
        return this;
    }

    public BaseRecyclerHolder startAnimation(int viewId){
        ImageView tv = getView(viewId);
        tv.setImageResource(R.drawable.loading_more);
        AnimationDrawable animationDrawable = (AnimationDrawable) tv.getDrawable();
        animationDrawable.start();
        return this;
    }


    public BaseRecyclerHolder setProgress(int viewId,long countLength,long readLength){
        NumberProgressBar progressBar = getView(viewId);
        progressBar.setMax((int) countLength);
        progressBar.setProgress((int) readLength);
        return this;
    }

    public BaseRecyclerHolder setHtmlText(int viewId,String text){
        TextView tv = getView(viewId);
        if(text==null){
            tv.setVisibility(View.GONE);
        }else{
            tv.setText(Html.fromHtml(text));
        }
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setFrameLayoutImageResource(int viewId,int drawableId){
        FrameLayout iv = getView(viewId);
        iv.setBackgroundResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setTextViewColor(int viewId,int color){
        TextView iv = getView(viewId);
        iv.setTextColor(color);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setImageByUrl(int viewId,String url){
        ImageView iv = getView(viewId);
        GlideImageLoader.loadImage(context,url,iv);
        return this;
    }

    public BaseRecyclerHolder setImageByUrlNone(int viewId,String url){
        ImageView iv = getView(viewId);
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.pic_default)
                .into(iv);
        return this;
    }

    /**
     * 卷轴模式阅读的recycleview 需要设置宽高
     * @param viewId
     * @param url
     * @return
     */
    public BaseRecyclerHolder setPhotoViewImageByUrl(int viewId, final String url){
        final ImageView iv = getView(viewId);
        final DiskCacheStrategy cache;//判断图片来自SD卡还是网络
        if(url.substring(1,8).equals("storage")){
            cache = DiskCacheStrategy.NONE;
        }else{
        }
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        return this;
    }

    public BaseRecyclerHolder setVisibility(int viewId,int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }

    public BaseRecyclerHolder setTextViewAppearanceColor(int viewId, int resId) {
        TextView iv = getView(viewId);
        iv.setTextAppearance(context,resId);
        return this;
    }
}