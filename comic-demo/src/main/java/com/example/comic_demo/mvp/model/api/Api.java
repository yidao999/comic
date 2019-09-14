package com.example.comic_demo.mvp.model.api;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 19:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {
    String APP_DOMAIN = "https://api.github.com";

    public static String TencentHomePage = "http://ac.qq.com/";
    public static String TencentJapanHot = "http://ac.qq.com/Comic/all/state/pink/nation/4/search/hot/page/1";
    public static String TencentTopUrl = "http://ac.qq.com/Comic/all/state/pink/search/hot/page/";
    public static String TencentBanner = "http://m.ac.qq.com";
    public static String TencentDetail = "http://ac.qq.com/Comic/comicInfo/id/";
    public static String KukuComicBase = "http://comic3.kukukkk.com";
    public static String TencentRankUrl = "http://m.ac.qq.com/rank/index?";
    public static String TencentCategoryUrlHead = "http://ac.qq.com/Comic/all";
    public static String TencentCategoryUrlMiddle = "/state/pink";
    public static String TencentCategoryUrlFoot = "/search/time/page/";


}
