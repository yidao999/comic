package com.example.comic_demo.mvp.model;

import android.app.Application;
import android.util.Log;

import com.example.comic_demo.app.Constants;
import com.example.comic_demo.app.utils.TencentComicAnalysis;
import com.example.comic_demo.mvp.model.api.Api;
import com.example.comic_demo.mvp.model.entity.BoyHomeItem;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.ComicHomeItem;
import com.example.comic_demo.mvp.model.entity.GirlHomeItem;
import com.example.comic_demo.mvp.model.entity.HomeTitleEntity;
import com.example.comic_demo.mvp.model.entity.JapanHomeItem;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.HomeContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2019 11:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public void loadData(Observer<List<Comic>> observer) {
        Observable ComicListObservable = Observable.create(new ObservableOnSubscribe<List<Comic>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Comic>> observableEmitter) throws Exception {
                try {
                    Document recommend = Jsoup.connect(Api.TencentHomePage).get();
                    Document japan = Jsoup.connect(Api.TencentJapanHot).get();
                    Document doc = Jsoup.connect(Api.TencentTopUrl + "1").get();
                    List<Comic>  mdats = new ArrayList<>();
                    List<BoyHomeItem> boysComicInfo = TencentComicAnalysis.TransToBoysComic(recommend);
                    List<GirlHomeItem> girlComicInfo = TencentComicAnalysis.TransToGirlsComic(recommend);
                    List<JapanHomeItem> japanComicInfo = TencentComicAnalysis.TransToJapanComic(japan);
                    List<ComicHomeItem> comicComicInfo = TencentComicAnalysis.TransToComic1(doc);
                    mdats.addAll(boysComicInfo);
                    mdats.addAll(girlComicInfo);
                    mdats.addAll(japanComicInfo);
                    mdats.addAll(comicComicInfo);
                    observableEmitter.onNext(mdats);
                } catch (Exception e) {
                    observableEmitter.onError(e);
                    e.printStackTrace();
                }finally {
                    observableEmitter.onComplete();
                }
            }

        });
        ComicListObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}