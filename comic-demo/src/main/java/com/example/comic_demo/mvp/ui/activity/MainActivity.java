package com.example.comic_demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.comic_demo.app.utils.setStatusBarUtils;
import com.example.comic_demo.mvp.ui.fragment.BookShelfFragment;
import com.example.comic_demo.mvp.ui.fragment.HomeFragment;
import com.example.comic_demo.mvp.ui.fragment.MineFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.comic_demo.di.component.DaggerMainComponent;
import com.example.comic_demo.mvp.contract.MainContract;
import com.example.comic_demo.mvp.presenter.MainPresenter;

import com.example.comic_demo.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2019 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;
    @BindView(R.id.bv_bottomNavigation)
    BottomNavigationView bv_bottomNavigation;

    List<Fragment> fragments;
    private FragmentTransaction mFragmentTransaction;
    private boolean isPress;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isPress = false;
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initFragment();
        listenerBottomNavigation();
    }

    private void listenerBottomNavigation() {
        bv_bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homepage_select:
                        selectFragment(0);
                        break;
                    case R.id.bookshelf_select:
                        selectFragment(1);
                        break;
                    case R.id.mine_select:
                        selectFragment(2);
                        break;
                }
                return true;
            }
        });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(BookShelfFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        selectFragment(0);
    }

    private void selectFragment(int i) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int j = 0; j < fragments.size(); j++) {
            mFragmentTransaction.hide(fragments.get(j));
        }
        if (!fragments.get(i).isAdded()) {
            mFragmentTransaction.add(R.id.fragment_container, fragments.get(i));
            mFragmentTransaction.show(fragments.get(i));
        } else {
            mFragmentTransaction.show(fragments.get(i));
        }
        mFragmentTransaction.commit();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isPress) {
            isPress = true;
            showMessage("再按一次返回退出");
            handler.sendEmptyMessageDelayed(0,2000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
