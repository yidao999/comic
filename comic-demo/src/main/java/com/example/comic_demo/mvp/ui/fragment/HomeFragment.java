package com.example.comic_demo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.comic_demo.app.utils.GlideImageLoader;
import com.example.comic_demo.app.utils.setStatusBarUtils;
import com.example.comic_demo.mvp.model.entity.BannerItem;
import com.example.comic_demo.mvp.model.entity.BoyHomeItem;
import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.ComicHomeItem;
import com.example.comic_demo.mvp.model.entity.GirlHomeItem;
import com.example.comic_demo.mvp.model.entity.HomeTitleEntity;
import com.example.comic_demo.mvp.model.entity.JapanHomeItem;
import com.example.comic_demo.mvp.ui.activity.RankActivity;
import com.example.comic_demo.mvp.ui.binder.HomeBoyBinder;
import com.example.comic_demo.mvp.ui.binder.HomeComicBinder;
import com.example.comic_demo.mvp.ui.binder.HomeGirlBinder;
import com.example.comic_demo.mvp.ui.binder.HomeHeadBinder;
import com.example.comic_demo.mvp.ui.binder.HomeJapanBinder;
import com.example.comic_demo.mvp.ui.binder.HomeTitleBinder;
import com.example.comic_demo.mvp.ui.widget.CustomPtrHeader;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.comic_demo.di.component.DaggerHomeComponent;
import com.example.comic_demo.mvp.contract.HomeContract;
import com.example.comic_demo.mvp.presenter.HomePresenter;

import com.example.comic_demo.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.rl_error_view)
    RelativeLayout error_view;
    @BindView(R.id.iv_error)
    ImageView iv_error;

    private MultiTypeAdapter mAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initStatus();
        initRecyclerView();
        initPtrFrame();
        initListener();
    }

    private void initListener() {
        iv_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                showMessage("加载中...");
            }
        });
    }

    private void initStatus() {
        setStatusBarUtils setStatusBarUtils = new setStatusBarUtils(getActivity());
        setStatusBarUtils.setStatusBarFullTransparent();
    }

    private void initPtrFrame() {
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
        mPtrFrame.disableWhenHorizontalMove(true);
        CustomPtrHeader header = new CustomPtrHeader(getContext());
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
    }

    private void loadData() {
        mPresenter.loadData();
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                Object o = mAdapter.getItems().get(i);
                if (o instanceof BoyHomeItem) {
                    return 1;
                } else if (o instanceof GirlHomeItem) {
                    return 1;
                } else if (o instanceof ComicHomeItem) {
                    return 1;
                } else {
                    return 3;
                }
            }
        };
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mAdapter = new MultiTypeAdapter();

        mAdapter.register(BannerItem.class, new HomeHeadBinder());
        mAdapter.register(HomeTitleEntity.class, new HomeTitleBinder());
        mAdapter.register(BoyHomeItem.class, new HomeBoyBinder());
        mAdapter.register(GirlHomeItem.class, new HomeGirlBinder());
        mAdapter.register(JapanHomeItem.class, new HomeJapanBinder());
        mAdapter.register(ComicHomeItem.class, new HomeComicBinder());

        mRecycleView.setAdapter(mAdapter);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        mPtrFrame.setPullToRefresh(true);
        mPtrFrame.autoRefresh(true);
    }

    @Override
    public void hideLoading() {
        error_view.setVisibility(View.GONE);
        mPtrFrame.refreshComplete();
    }

    @Override
    public void errorLoading() {
        error_view.setVisibility(View.VISIBLE);
        mPtrFrame.refreshComplete();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void finishTask(Items items) {
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

}
