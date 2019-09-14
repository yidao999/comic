package com.example.comic_demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.comic_demo.app.utils.setStatusBarUtils;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.ui.adapter.RankAdapter;
import com.example.comic_demo.mvp.ui.adapter.base.BaseRecyclerAdapter;
import com.example.comic_demo.mvp.ui.widget.CustomTab;
import com.example.comic_demo.mvp.ui.widget.ElasticScrollView;
import com.example.comic_demo.mvp.ui.widget.NoScrollGridLayoutManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.comic_demo.di.component.DaggerRankComponent;
import com.example.comic_demo.mvp.contract.RankContract;
import com.example.comic_demo.mvp.presenter.RankPresenter;

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
 * Created by MVPArmsTemplate on 09/11/2019 13:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RankActivity extends BaseActivity<RankPresenter> implements RankContract.View {

    @BindView(R.id.rv_bookshelf)
    RecyclerView mRecycleView;
    @BindView(R.id.ev_scrollview)
    ElasticScrollView mScrollView;
    @BindView(R.id.ll_actionbar)
    CustomTab mTab;

    RankAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRankComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rank; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initStatus();
        initRecyclerView();
        mPresenter.loadData();
    }

    private void initStatus() {
        setStatusBarUtils setStatusBarUtils = new setStatusBarUtils(this);
        setStatusBarUtils.setStatusBarFullTransparent();
    }

    private void initRecyclerView() {
        final NoScrollGridLayoutManager layoutManager = new NoScrollGridLayoutManager(this, 1);
        layoutManager.setScrollEnabled(false);
        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new RankAdapter(this, R.layout.item_rank, R.layout.item_loading);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if(position!=mAdapter.getItemCount()-1){
                    Comic comic = mAdapter.getItems(position);
                    ComicDetailActivity.launchActivity(RankActivity.this,comic.getId()+"",comic.getTitle());
                }
            }
        });
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

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void fillData(List<Comic> data) {
        List<Comic> data1 = new ArrayList<>();
        Comic comic = new Comic();
        comic.setTitle("测试");
        data1.add(comic);
        mAdapter.updateWithClear(data);
    }

    @Override
    public void getDataFinish() {
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.rl_update, R.id.rl_sellgood, R.id.rl_hot, R.id.rl_mouth
            , R.id.iv_back_color,R.id.iv_search
    })
    public void getType(View view) {
        switch (view.getId()) {
            case R.id.rl_update:
                mPresenter.setType("upt");
                break;
            case R.id.rl_sellgood:
                mPresenter.setType("pay");
                break;
            case R.id.rl_hot:
                mPresenter.setType("pgv");
                break;
            case R.id.rl_mouth:
                mPresenter.setType("mt");
                break;
            case R.id.iv_back_color:
                finish();
                break;
            case R.id.iv_search:
                // TODO: 2019/9/11  搜索按钮
                break;
        }
    }

    @Override
    public void setType(int position) {
        mTab.setCurrentPosition(position);
    }
}
