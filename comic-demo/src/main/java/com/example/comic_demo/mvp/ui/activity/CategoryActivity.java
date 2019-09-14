package com.example.comic_demo.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.comic_demo.app.utils.setStatusBarUtils;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.Type;
import com.example.comic_demo.mvp.ui.adapter.CategoryAdapter;
import com.example.comic_demo.mvp.ui.adapter.CategoryListAdapter;
import com.example.comic_demo.mvp.ui.adapter.base.BaseRecyclerAdapter;
import com.example.comic_demo.mvp.ui.widget.ElasticHeadScrollView;
import com.example.comic_demo.mvp.ui.widget.NoScrollGridLayoutManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.comic_demo.di.component.DaggerCategoryComponent;
import com.example.comic_demo.mvp.contract.CategoryContract;
import com.example.comic_demo.mvp.presenter.CategoryPresenter;

import com.example.comic_demo.R;


import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 22:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CategoryActivity extends BaseActivity<CategoryPresenter> implements CategoryContract.View {

    @BindView(R.id.rv_select)
    RecyclerView mSelectRecyclerView;

    @BindView(R.id.rv_bookshelf)
    RecyclerView mSelectListRecyclerView;
    @BindView(R.id.ev_scrollview)
    ElasticHeadScrollView mScrollView;
    @BindView(R.id.tv_actionbar_category)
    TextView mCategoryText;
    @BindView(R.id.rl_actionbar_category)
    RelativeLayout mCategoryRelativeLayout;

    CategoryAdapter mSelectAdapter;

    CategoryListAdapter mCategoryAdapter;
    private static Intent intent;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCategoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_category; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initStatus();
        mPresenter.setIntent(intent);
        initRecyclerView();
        initListener();
        mPresenter.loadData();
    }

    private void initStatus() {
        setStatusBarUtils setStatusBarUtils = new setStatusBarUtils(this);
        setStatusBarUtils.setStatusBarFullTransparent();
    }

    private void initListener() {
        mScrollView.setListener(new ElasticHeadScrollView.OnScrollListener() {
            @Override
            public void OnScrollToBottom() {
                mPresenter.loadCategoryList();
            }

            @Override
            public void onAlphaActionBar(float a) {
                mCategoryRelativeLayout.setAlpha(a);
            }
        });

        mSelectAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Type type = mSelectAdapter.getItems(position);
                mPresenter.onItemClick(type);
            }
        });

        mCategoryAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if(position!=mCategoryAdapter.getItemCount()-1&&position>=0){//蒲公英carsh修改，防止数组越界
                    Comic comic = mCategoryAdapter.getItems(position);
                    ComicDetailActivity.launchActivity(CategoryActivity.this,comic.getId()+"",comic.getTitle());
                }
            }
        });
    }

    private void initRecyclerView() {
        mSelectAdapter = new CategoryAdapter(this,R.layout.item_categroy_select);
        mCategoryAdapter = new CategoryListAdapter(this,R.layout.item_homepage_three,R.layout.item_loading);

        NoScrollGridLayoutManager gridLayoutManager = new NoScrollGridLayoutManager(this,7);
        gridLayoutManager.setScrollEnabled(false);
        mSelectRecyclerView.setLayoutManager(gridLayoutManager);
        mSelectRecyclerView.setAdapter(mSelectAdapter);

        NoScrollGridLayoutManager ListgridLayoutManager = new NoScrollGridLayoutManager(this,3);
        ListgridLayoutManager.setScrollEnabled(false);
        mSelectListRecyclerView.setLayoutManager(ListgridLayoutManager);
        mSelectListRecyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    public static void launchActivity(Context context,String type,int value) {
        intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(type,value);
        context.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void fillSelectData(List<Type> mList, Map<String, Integer> mMap) {
        mSelectAdapter.setSelectMap(mMap);
        mSelectAdapter.updateWithClear(mList);
        mSelectAdapter.notifyDataSetChanged();
        mCategoryText.setText(mPresenter.getTitle());
        //mScrollView.setInnerHeight();
    }

    @Override
    public void fillData(List<Comic> data) {
        mCategoryAdapter.updateWithClear(data);
        mCategoryAdapter.notifyDataSetChanged();
        //mScrollView.setInnerHeight();
    }

    @Override
    public void setMap(Map<String, Integer> mMap) {
        mSelectAdapter.setSelectMap(mMap);
        mCategoryText.setText(mPresenter.getTitle());
    }

    @OnClick(R.id.iv_back_color)
    public void finish(View view){
        super.finish();
    }

    @OnClick(R.id.iv_search)
    public void ToSearch(View view){
        // TODO: 2019/9/12
    }
}
