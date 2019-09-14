package com.example.comic_demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.comic_demo.di.module.BookShelfModule;
import com.example.comic_demo.mvp.contract.BookShelfContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.comic_demo.mvp.ui.fragment.BookShelfFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/12/2019 22:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = BookShelfModule.class, dependencies = AppComponent.class)
public interface BookShelfComponent {
    void inject(BookShelfFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BookShelfComponent.Builder view(BookShelfContract.View view);

        BookShelfComponent.Builder appComponent(AppComponent appComponent);

        BookShelfComponent build();
    }
}