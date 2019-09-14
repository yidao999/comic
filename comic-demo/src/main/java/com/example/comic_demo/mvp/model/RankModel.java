package com.example.comic_demo.mvp.model;

import android.app.Application;

import com.example.comic_demo.app.utils.TencentComicAnalysis;
import com.example.comic_demo.mvp.model.api.Api;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.RankContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Url;


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
public class RankModel extends BaseModel implements RankContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RankModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public void getRankList(long currenttime, String type, int page, Observer observer) {
        Observable.create(new ObservableOnSubscribe<List<Comic>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Comic>> emitter) throws Exception {
                try {
                    Document doc = Jsoup.connect(Api.TencentRankUrl + "t=" + currenttime + "&type=" + type + "&page=" + page + "&pageSize=10&style=items").get();
                    List<Comic> mdats = TencentComicAnalysis.TransToRank(doc);
                    emitter.onNext(mdats);
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                } finally {
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}