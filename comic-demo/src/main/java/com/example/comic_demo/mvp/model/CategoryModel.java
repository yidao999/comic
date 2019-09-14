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

import com.example.comic_demo.mvp.contract.CategoryContract;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Url;


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
public class CategoryModel extends BaseModel implements CategoryContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CategoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public void getCategroyList(Map<String, Integer> mSelectMap, int page, Observer observer) {
        Observable.create(new ObservableOnSubscribe<List<Comic>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Comic>> observableEmitter) throws Exception {
                try {
                    String theme= "";
                    String audience = "";
                    String finish = "";
                    String nation = "";
                    if(mSelectMap.get("theme")!=0){
                        theme = "/theme/"+mSelectMap.get("theme");
                    }
                    if(mSelectMap.get("audience")!=0){
                        audience = "/audience/"+mSelectMap.get("audience");
                    }
                    if(mSelectMap.get("finish")!=0){
                        finish = "/finish/"+mSelectMap.get("finish");
                    }
                    if(mSelectMap.get("nation")!=0){
                        nation = "/nation/"+mSelectMap.get("nation");
                    }
                    String url = Api.TencentCategoryUrlHead+audience+theme+finish+Api.TencentCategoryUrlMiddle+nation+Api.TencentCategoryUrlFoot+page;
                    Document doc = Jsoup.connect(url).get();
                    List<Comic> mdats = TencentComicAnalysis.TransToComic2(doc);
                    observableEmitter.onNext(mdats);
                } catch (Exception e) {
                    observableEmitter.onError(e);
                    e.printStackTrace();
                }finally {
                    observableEmitter.onComplete();
                }
            }
        }) .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}