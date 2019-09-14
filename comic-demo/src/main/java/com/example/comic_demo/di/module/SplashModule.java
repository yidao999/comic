package com.example.comic_demo.di.module;

import android.support.v4.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.comic_demo.mvp.contract.SplashContract;
import com.example.comic_demo.mvp.model.SplashModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 19:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SplashModule {

    @Binds
    abstract SplashContract.Model bindSplashModel(SplashModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(SplashContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
}