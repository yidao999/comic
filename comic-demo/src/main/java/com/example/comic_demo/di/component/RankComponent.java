package com.example.comic_demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.comic_demo.di.module.RankModule;
import com.example.comic_demo.mvp.contract.RankContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.comic_demo.mvp.ui.activity.RankActivity;


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
@ActivityScope
@Component(modules = RankModule.class, dependencies = AppComponent.class)
public interface RankComponent {
    void inject(RankActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RankComponent.Builder view(RankContract.View view);

        RankComponent.Builder appComponent(AppComponent appComponent);

        RankComponent build();
    }
}