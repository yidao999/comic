package com.example.comic_demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.comic_demo.di.module.CategoryModule;
import com.example.comic_demo.mvp.contract.CategoryContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.comic_demo.mvp.ui.activity.CategoryActivity;


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
@ActivityScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CategoryComponent.Builder view(CategoryContract.View view);

        CategoryComponent.Builder appComponent(AppComponent appComponent);

        CategoryComponent build();
    }
}