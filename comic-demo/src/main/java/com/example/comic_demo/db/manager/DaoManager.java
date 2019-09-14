package com.example.comic_demo.db.manager;

import android.content.Context;

import com.android.zhr.greendao.gen.DaoMaster;
import com.android.zhr.greendao.gen.DaoSession;
import com.example.comic_demo.app.Constants;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * author: 小川
 * Date: 2019/9/1
 * Description:
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static volatile DaoManager manager;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoSession daoSession;
    private Context mContext;
    private DaoManager(Context context) {
        this.mContext = context.getApplicationContext();
    }
    public static DaoManager getInstance(Context context) {
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (manager == null) {
                    manager = new DaoManager(context);
                }
            }
        }
        return manager;
    }
    private static DaoMaster daoMaster;
    //判断是否存在数据库，没有就创建
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            helper = new DaoMaster.DevOpenHelper(mContext, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDb());
        }
        return daoMaster;
    }
    //   数据处理
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
    //输出日志
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
    public void close() {
        closeHelper();
        closeSession();
    }
    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }
    //关闭session
    public void closeSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }
}
