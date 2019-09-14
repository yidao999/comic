package com.example.comic_demo.mvp.presenter;

import android.app.Application;

import com.example.comic_demo.app.Constants;
import com.example.comic_demo.app.utils.ShowErrorTextUtil;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.observers.DisposableObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.ComicDetailContract;


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
public class ComicDetailPresenter extends BasePresenter<ComicDetailContract.Model, ComicDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private long mComicId;
    //默认漫画都来自腾讯
    private int from = 0;
    private Comic mComic;

    @Inject
    public ComicDetailPresenter(ComicDetailContract.Model model, ComicDetailContract.View rootView) {
        super(model, rootView);
        mComic = new Comic();
    }

    /**
     * 加载详情
     */
    public void getDetail(String comicId) {
        if (comicId == null) {
            mRootView.showMessage("获取ID失败");
        } else {
            mComicId = Long.parseLong(comicId);
            mModel.getComicDetail(comicId, from, new DisposableObserver<Comic>() {

                @Override
                public void onError(Throwable throwable) {
                    mRootView.showMessage(ShowErrorTextUtil.ShowErrorText(throwable));
                }

                @Override
                public void onComplete() {
                    mRootView.hideLoading();
                }

                @Override
                public void onNext(Comic comic) {
                    mRootView.showLoading();
                    comic.setId(mComicId);
                    mComic = comic;
                    // TODO: 2019/9/5
                    //                    SaveComicToDB(mComic);
                    mRootView.fillData(comic);
                    //mView.ShowToast("之前看到"+(mComic.getCurrentChapter())+"话");
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
