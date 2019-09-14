package com.example.comic_demo.mvp.contract;

import com.example.comic_demo.mvp.model.entity.Comic;
import com.example.comic_demo.mvp.model.entity.Type;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;


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
public interface CategoryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void fillSelectData(List<Type> mList, Map<String, Integer> mMap);
        void fillData(List<Comic> data);
        void setMap(Map<String, Integer> mMap);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        void getCategroyList(final Map<String, Integer> mSelectMap, final int page, Observer observer);
    }
}
