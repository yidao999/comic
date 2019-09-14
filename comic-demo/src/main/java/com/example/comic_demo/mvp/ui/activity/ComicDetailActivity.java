package com.example.comic_demo.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.comic_demo.app.Constants;
import com.example.comic_demo.app.utils.setStatusBarUtils;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.ui.adapter.DetailCatalogAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.comic_demo.di.component.DaggerComicDetailComponent;
import com.example.comic_demo.mvp.contract.ComicDetailContract;
import com.example.comic_demo.mvp.presenter.ComicDetailPresenter;

import com.example.comic_demo.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 21:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ComicDetailActivity extends BaseActivity<ComicDetailPresenter> implements ComicDetailContract.View {

    @BindView(R.id.iv_image)
    ImageView mHeaderView;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.ll_text)
    LinearLayout mText;
    @BindView(R.id.tv_author_tag)
    TextView mAuthorTag;
    @BindView(R.id.tv_collects)
    TextView mCollects;
    @BindView(R.id.tv_describe)
    TextView mDescribe;
    @BindView(R.id.tv_popularity)
    TextView mPopularity;
    @BindView(R.id.tv_status)
    TextView mStatus;
    @BindView(R.id.tv_update)
    TextView mUpdate;
    @BindView(R.id.tv_point)
    TextView mPoint;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.iv_oreder2)
    ImageView mOrder2;
    @BindView(R.id.btn_read)
    Button mRead;
    @BindView(R.id.iv_error)
    ImageView mReload;
    @BindView(R.id.iv_collect)
    ImageView mCollect;
    @BindView(R.id.tv_collect)
    TextView mIsCollect;

    private String comicTitle;
    private String comicId;
    private Rect normal = new Rect();
    private RecyclerView mRecyclerView;
    private RelativeLayout mRLloading;
    private ImageView mLoading;
    private TextView mTv_loading_title;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerComicDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comic_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initStatus();
        initIntent();
        initView1();
        loadTitle();
        initRecyclerView();
        loadNetData();
    }

    private void initView1() {
        mLoading = findViewById(R.id.iv_loading);
        mRLloading = findViewById(R.id.rl_loading);
        mTv_loading_title = findViewById(R.id.tv_loading_title);
        mLoading.setImageResource(R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) mLoading.getDrawable();
        animationDrawable.start();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycle_view);
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(this));
    }

    private void initStatus() {
        setStatusBarUtils setStatusBarUtils = new setStatusBarUtils(this);
        setStatusBarUtils.setStatusBarFullTransparent();
    }

    private void loadNetData() {
        mPresenter.getDetail(comicId);
    }

    private void loadTitle() {
        if (!TextUtils.isEmpty(comicTitle)) {
            mTitle.setText(comicTitle);
            mTv_loading_title.setText(comicTitle);
        }
    }

    private void initIntent() {
        comicTitle = getIntent().getStringExtra(Constants.COMIC_TITLE);
        comicId = getIntent().getStringExtra(Constants.COMIC_ID);

    }

    @Override
    public void showLoading() {
        mRLloading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mRLloading.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    public static void launchActivity(Activity context, String id, String title) {
        Intent intent = new Intent(context, ComicDetailActivity.class);
        intent.putExtra(Constants.COMIC_ID, id);
        intent.putExtra(Constants.COMIC_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    public void fillData(final Comic comic) {
        Glide.with(this)
                .load(comic.getCover())
                .placeholder(R.mipmap.pic_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mHeaderView);
        mTitle.setText(comic.getTitle());
        mAuthorTag.setText(comic.getAuthor() + comic.getTags().toString());
        mCollects.setText(comic.getCollections());
        mDescribe.setText(comic.getDescribe());
        mStatus.setText(comic.getStatus());
        mPopularity.setText(comic.getPopularity());
        mUpdate.setText(comic.getUpdates());
        mPoint.setText(comic.getPoint());
        normal.set(mText.getLeft(), mText.getTop(), ArmsUtils.getScreenWidth(this), mText.getBottom());
        setRecyclerView(comic);
    }

    private void setRecyclerView(Comic comic) {
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < comic.getChapters().size(); i++) {
            String title = comic.getChapters().get(i);
            mData.add((i) + "-" + title);
        }

        mRecyclerView.setAdapter(new DetailCatalogAdapter(mData));
    }

}
