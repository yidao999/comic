package com.example.comic_demo.mvp.presenter;

import android.app.Application;

import com.example.comic_demo.app.utils.ShowErrorTextUtil;
import com.example.comic_demo.app.utils.TencentComicAnalysis;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.LoadingItem;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import retrofit2.http.Url;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.RankContract;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


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
public class RankPresenter extends BasePresenter<RankContract.Model, RankContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private String type = "upt";
    private int page = 1;
    private List<Comic> mList;


    @Inject
    public RankPresenter(RankContract.Model model, RankContract.View rootView) {
        super(model, rootView);
        mList = new ArrayList<>();
    }

    public void loadData() {
        mModel.getRankList(getCurrentTime(), type, page, new DisposableObserver<List<Comic>>() {

            @Override
            public void onNext(List<Comic> comics) {
                mList.addAll(comics);
                mRootView.fillData(mList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRootView.showMessage(ShowErrorTextUtil.ShowErrorText(e));
            }

            @Override
            public void onComplete() {
                mRootView.getDataFinish();
                page++;
                int position = 0;
                if (type.equals("upt")) {
                    position = 0;
                } else if (type.equals("pay")) {
                    position = 1;
                } else if (type.equals("pgv")) {
                    position = 2;
                } else {
                    position = 3;
                }
                mRootView.setType(position);
            }
        });
    }

    public void setType(String type) {
        this.type = type;
        this.mList.clear();
        this.page = 1;
        loadData();
    }

    public String getType() {
        return type;
    }

    public long getCurrentTime() {
        java.util.Date date = new java.util.Date();
        long datetime = date.getTime();
        return datetime;
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
