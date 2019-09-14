package com.example.comic_demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.comic_demo.di.module.ComicDetailModule;
import com.example.comic_demo.mvp.contract.ComicDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.comic_demo.mvp.ui.activity.ComicDetailActivity;


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
@ActivityScope
@Component(modules = ComicDetailModule.class, dependencies = AppComponent.class)
public interface ComicDetailComponent {
    void inject(ComicDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ComicDetailComponent.Builder view(ComicDetailContract.View view);

        ComicDetailComponent.Builder appComponent(AppComponent appComponent);

        ComicDetailComponent build();
    }
}