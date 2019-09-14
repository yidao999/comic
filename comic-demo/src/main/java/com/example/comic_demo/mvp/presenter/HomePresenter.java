package com.example.comic_demo.mvp.presenter;

import android.app.Application;

import com.example.comic_demo.app.utils.ShowErrorTextUtil;
import com.example.comic_demo.app.utils.TencentComicAnalysis;
import com.example.comic_demo.mvp.model.api.Api;
import com.example.comic_demo.mvp.model.entity.BannerItem;
import com.example.comic_demo.mvp.model.entity.BoyHomeItem;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.ComicHomeItem;
import com.example.comic_demo.mvp.model.entity.JapanHomeItem;
import com.example.comic_demo.mvp.model.entity.GirlHomeItem;
import com.example.comic_demo.mvp.model.entity.HomeTitleEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import me.drakeet.multitype.Items;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.HomeContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


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
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private List<HomeTitleEntity> homeTitlesInfo;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void loadData() {
        initTitle();
        initJsoupConnect();
    }

    private void initJsoupConnect() {
        mRootView.showLoading();
        mModel.loadData(new DisposableObserver<List<Comic>>() {
            @Override
            public void onNext(List<Comic> comics) {
                List<BoyHomeItem> boysComicInfo = new ArrayList<>();
                List<GirlHomeItem> girlComicInfo = new ArrayList<>();
                List<JapanHomeItem> japanComicInfo = new ArrayList<>();
                List<ComicHomeItem> comicComicInfo = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    boysComicInfo.add((BoyHomeItem) comics.get(i));
                }
                for (int i = 6; i < 12; i++) {
                    girlComicInfo.add((GirlHomeItem) comics.get(i));
                }
                for (int i = 12; i < 15; i++) {
                    japanComicInfo.add((JapanHomeItem) comics.get(i));
                }
                for (int i = 15; i < 27; i++) {
                    comicComicInfo.add((ComicHomeItem) comics.get(i));
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document doc = Jsoup.connect(Api.TencentBanner).get();
                            List<Comic> bannerCommic = TencentComicAnalysis.TransToBanner(doc);
                            mAppManager.getCurrentActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initItems(bannerCommic,boysComicInfo, girlComicInfo, japanComicInfo, comicComicInfo);
                                }
                            });
                        }catch (Throwable e){

                        }
                    }
                }).start();
            }

            @Override
            public void onError(Throwable e) {
                mRootView.errorLoading();
                mRootView.showMessage(ShowErrorTextUtil.ShowErrorText(e));
            }

            @Override
            public void onComplete() {
            }
        });

    }

    private void initItems(List<Comic> bannerCommic, List<BoyHomeItem> boysComicInfo, List<GirlHomeItem> girlComicInfo, List<JapanHomeItem> japanComicInfo, List<ComicHomeItem> comicComicInfo) {
        mRootView.hideLoading();
        Items items = new Items();
        BannerItem bannerItem = new BannerItem();
        bannerItem.setComics(bannerCommic);
        items.add(bannerItem);
        items.add(homeTitlesInfo.get(0));
        items.add(homeTitlesInfo.get(1));
        for (BoyHomeItem boyInfo : boysComicInfo) {
            items.add(boyInfo);
        }
        items.add(homeTitlesInfo.get(2));

        for (GirlHomeItem girlInfo : girlComicInfo) {
            items.add(girlInfo);
        }
        items.add(homeTitlesInfo.get(3));
        for (JapanHomeItem japanInfo : japanComicInfo) {
            items.add(japanInfo);
        }
        items.add(homeTitlesInfo.get(4));

        for (ComicHomeItem comicInfo : comicComicInfo) {
            items.add(comicInfo);
        }

        mRootView.finishTask(items);
    }

    private void initTitle() {
        homeTitlesInfo = new ArrayList<>();
        String[] str = {"热门连载", "少年漫画", "少女漫画", "日漫馆", "排行榜"};
        for (int i = 0; i < str.length; i++) {
            HomeTitleEntity homeTitle = new HomeTitleEntity();
            homeTitle.setItemTitle(str[i]);
            homeTitlesInfo.add(homeTitle);
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
