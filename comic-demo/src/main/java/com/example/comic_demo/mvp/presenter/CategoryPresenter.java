package com.example.comic_demo.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.example.comic_demo.app.Constants;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.LoadingItem;
import com.example.comic_demo.mvp.model.entity.Type;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.example.comic_demo.mvp.contract.CategoryContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class CategoryPresenter extends BasePresenter<CategoryContract.Model, CategoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<Type> mSelectList;
    private Map<String,Integer> mSelectMap;
    private int page;

    private List<Comic> mList;
    private boolean isloadingdata;

    private String mCategroyTitle;

    private String[] themes,finish,audience,nation;

    @Inject
    public CategoryPresenter(CategoryContract.Model model, CategoryContract.View rootView) {
        super(model, rootView);
        this.mSelectList = new ArrayList<>();
        this.mList = new ArrayList<>();
        this.mSelectMap =new HashMap<>();
        this.isloadingdata = false;
        this.page = 1;
    }

    public void loadData() {
        themes = new String[]{"全部","爆笑","热血","冒险","恐怖","科幻","魔幻","玄幻","校园","悬疑","推理","萌系","穿越","后宫"};
        for(int i=0;i<14;i++){
            Type item = new Type(Constants.CATEGORY_TITLE_THEME,themes[i],i);
            mSelectList.add(item);
        }
        finish =  new String[]{"全部","连载","完结",null,null,null,null};
        for(int i=0;i<7;i++){
            Type item = new Type(Constants.CATEGORY_TITLE_FINISH,finish[i],i);
            mSelectList.add(item);
        }
        audience =  new String[]{"全部","少年","少女","青年","少儿",null,null};
        for(int i=0;i<7;i++){
            Type item = new Type(Constants.CATEGORY_TITLE_AUDIENCE,audience[i],i);
            mSelectList.add(item);
        }
        nation =  new String[]{"全部","内地","港台","韩国","日本",null,null};
        for(int i=0;i<7;i++){
            Type item = new Type(Constants.CATEGORY_TITLE_NATION,nation[i],i);
            mSelectList.add(item);
        }
        mRootView.fillSelectData(mSelectList,mSelectMap);
        loadCategoryList();
    }

    public void loadCategoryList(){
        if(!isloadingdata){
            mModel.getCategroyList(mSelectMap,page, new DisposableObserver<List<Comic>>() {
                @Override
                protected void onStart() {
                    super.onStart();
                    isloadingdata = true;
                }

                @Override
                public void onNext(@NonNull List<Comic> comics) {
                    mList.addAll(comics);
                    List<Comic> temp = new ArrayList<>(mList);
                    if(comics.size()==12){
                        temp.add(new LoadingItem(true));
                        mRootView.fillData(temp);
                        isloadingdata = false;
                    }else{
                        temp.add(new LoadingItem(false));
                        mRootView.fillData(temp);
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    page++;
                }
            });
        }
    }

    public void onItemClick(Type type) {
        if(type.getTitle()!=null){
            mSelectMap.put(type.getType(),type.getValue());
            mRootView.setMap(mSelectMap);
            this.mList.clear();
            this.page = 1;
            this.isloadingdata = false;
            loadCategoryList();
        }
    }

    public String getTitle(){
        mCategroyTitle = "·精品";
        if(mSelectMap.get(Constants.CATEGORY_TITLE_THEME)!=0){
            mCategroyTitle = mCategroyTitle +"&"+ themes[mSelectMap.get(Constants.CATEGORY_TITLE_THEME)];
        }
        if(mSelectMap.get(Constants.CATEGORY_TITLE_FINISH)!=0){
            mCategroyTitle = mCategroyTitle +"&"+ finish[mSelectMap.get(Constants.CATEGORY_TITLE_FINISH)];
        }
        if(mSelectMap.get(Constants.CATEGORY_TITLE_AUDIENCE)!=0){
            mCategroyTitle = mCategroyTitle +"&"+ audience[mSelectMap.get(Constants.CATEGORY_TITLE_AUDIENCE)];
        }
        if(mSelectMap.get(Constants.CATEGORY_TITLE_NATION)!=0){
            mCategroyTitle = mCategroyTitle +"&"+ nation[mSelectMap.get(Constants.CATEGORY_TITLE_NATION)];
        }
        mCategroyTitle = mCategroyTitle+"·";
        return mCategroyTitle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setIntent(Intent intent){
        mSelectMap.put(Constants.CATEGORY_TITLE_THEME,intent.getIntExtra(Constants.CATEGORY_TITLE_THEME,0));
        mSelectMap.put(Constants.CATEGORY_TITLE_FINISH,intent.getIntExtra(Constants.CATEGORY_TITLE_FINISH,0));
        mSelectMap.put(Constants.CATEGORY_TITLE_AUDIENCE,intent.getIntExtra(Constants.CATEGORY_TITLE_AUDIENCE,0));
        mSelectMap.put(Constants.CATEGORY_TITLE_NATION,intent.getIntExtra(Constants.CATEGORY_TITLE_NATION,0));
    }
}
