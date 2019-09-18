package utils;

import android.app.Application;

import com.tencent.bugly.Bugly;

/**
 * @author Andlei
 * @date 2019/9/17.
 */
public  class  BaseAppUtils {
    public static Application App;
    public static String BaseUrl; //网络请求域名
    public static void init(Application application,String baseUrl){
        Bugly.init(application.getApplicationContext(), "14e0d57f7f", false);
        SetBaseUrl(baseUrl);
        App = application;
    }
    public static Application getInstance(){
        return App;
    }
    public static void SetBaseUrl(String baseurl){
        BaseUrl = baseurl;
    }
    public static String GetBaseUrl(){
        return BaseUrl;
    }
}
