package com.example.comic_demo.mvp.model;

import android.app.Application;
import android.content.Context;

import com.example.comic_demo.app.Constants;
import com.example.comic_demo.app.utils.KukuComicAnalysis;
import com.example.comic_demo.app.utils.NetworkUtils;
import com.example.comic_demo.app.utils.TencentComicAnalysis;
import com.example.comic_demo.db.helper.DaoHelper;
import com.example.comic_demo.mvp.model.api.Api;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.ComicDetailContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import retrofit2.http.Url;


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
public class ComicDetailModel extends BaseModel implements ComicDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    private final DaoHelper mHelper;
    private final Context context;

    @Inject
    public ComicDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        context = repositoryManager.getContext();
        mHelper = new DaoHelper(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public void getComicDetail(String mComicId, int from, Observer observer) {
        Observable.create(new ObservableOnSubscribe<Comic>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Comic> observableEmitter) throws Exception {
                try {
                    Comic comicFromDB = (Comic) mHelper.findComic(Long.parseLong(mComicId));
                    if(NetworkUtils.isAvailable(context)){
                        Comic mComic;
                        if(from == Constants.FROM_TENCENT){
                            Document doc = Jsoup.connect(Api.TencentDetail+mComicId).get();
                            mComic = TencentComicAnalysis.TransToComicDetail(doc);
                            mComic.setFrom(from);
                        }else{
                            String url = Api.KukuComicBase+"/comiclist/"+(Long.parseLong(mComicId)/1000000);
                            Document doc = Jsoup.connect(url).get();
                            mComic = KukuComicAnalysis.TransToComicDetail(doc);
                            mComic.setFrom(from);
                        }

                        if(comicFromDB!=null) {
                            mComic.setCurrentChapter(comicFromDB.getCurrentChapter());
                            mComic.setStateInte(comicFromDB.getStateInte());
                            mComic.setDownloadTime(comicFromDB.getDownloadTime());
                            mComic.setCollectTime(comicFromDB.getCollectTime());
                            mComic.setClickTime(comicFromDB.getClickTime());
                            mComic.setDownload_num(comicFromDB.getDownload_num());
                            mComic.setDownload_num_finish(comicFromDB.getDownload_num_finish());
                            mComic.setCurrent_page(comicFromDB.getCurrent_page());
                            mComic.setCurrent_page_all(comicFromDB.getCurrent_page_all());
                            mComic.setIsCollected(comicFromDB.getIsCollected());
                            mComic.setReadType(comicFromDB.getReadType());
                            mComic.setFrom(from);
                        }else{
                            mComic.setCurrentChapter(0);
                        }
                        observableEmitter.onNext(mComic);
                    }else{
                        if(comicFromDB !=null){
                            observableEmitter.onNext(comicFromDB);
                        }else{
                            observableEmitter.onError(new ConnectException());
                        }
                    }
                } catch (SocketTimeoutException e){
                    observableEmitter.onError(e);
                } catch (Exception e) {
                    if(e instanceof InterruptedIOException){//如果线程错误不做任何处理

                    }else{
                        observableEmitter.onError(e);
                    }
                }finally {
                    observableEmitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}