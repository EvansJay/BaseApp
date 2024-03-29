package netWork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.Toast;


import com.andlei.baselibrary.BuildConfig;
import com.andlei.baselibrary.R;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import base.BaseBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.BaseAppUtils;


/**
 * 《一个Android工程的从零开始》
 *
 * @author 半寿翁
 * @博客：
 * @CSDN http://blog.csdn.net/u010513377/article/details/74455960
 * @简书 http://www.jianshu.com/p/1410051701fe
 */
public class NetUtils {
    /**
     * 上下文
     */
    private final Context mContext;
    /**
     * 网络请求结果回调
     */
    private final NetRequestCallBack netRequestCallBack;
    /**
     * header
     */
    private Map<String, String> headerParams;
    /**
     * 网络请求弹窗
     */


    public  NetUtils(Context mContext, NetRequestCallBack netRequestCallBack) {
        this.netRequestCallBack = netRequestCallBack;
        this.mContext = mContext;
    }

    /**
     * 初始化请求头，具体情况根据需求设置
     */
    public void initHeader() {
        headerParams = new HashMap<>();
        // 传参方式为json
        headerParams.put("Content-Type", "application/json");
    }

    /**
     * 初始化数据
     *
     * @param action 当前请求的尾址
     */
    private Retrofit initBaseData(final String action) {

        // https信任管理
        TrustManager[] trustManager = new TrustManager[] {
                new X509TrustManager() {

                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 请求超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        // 请求参数获取
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@android.support.annotation.NonNull Chain chain) throws IOException {
                Request request = chain.request();
                com.andlei.utils.Logger.i("zzz", String.format("request====%s", request.headers().toString()));
                com.andlei.utils.Logger.i("zzz", String.format("request====%s", request.toString()));
                okhttp3.Response proceed = chain.proceed(request);
                com.andlei.utils.Logger.i("zzz", String.format("proceed====%s", proceed.headers().toString()));
                return proceed;
            }
        });
        CookieManager cookieManager = new CookieManager(new com.andlei.netWork.InDiskCookieStore(mContext), CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        try {
            // https信任
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // 全部信任
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        // 构建Builder，请求结果RxJava接收，使用GSON转化为Bean，
        Retrofit.Builder builder1 = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        builder1.baseUrl(BaseAppUtils.GetBaseUrl() + action.substring(0, action.lastIndexOf("/") + 1));

        return builder1.build();
    }

    /**
     * Get请求
     *
     * @param action     网络请求尾址
     * @param params     请求参数
     * @param clazz      返回数据类
     * @param tag        请求复用时的判断标签
     * @param showDialog 是否展示请求加载框
     */
    public<T extends BaseBean> void get(final String action, Map<String, Object> params, Class<T> clazz, Map<String, ?> tag, boolean showDialog) {
        if (showDialog) {
            showLoadDialog();
        }

        com.andlei.netWork.RetrofitGetService getService = initBaseData(action).create(com.andlei.netWork.RetrofitGetService.class);
        if (params == null) {
            params = new HashMap<>();
        }

        if (null == headerParams) {
            headerParams = new HashMap<>();
        }

        com.andlei.utils.Logger.i("zzz", "request====" + new JSONObject(params));

        getService.getResult(action.substring(action.lastIndexOf("/") + 1), headerParams, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<>(action, clazz, tag, showDialog));
    }

    /**
     * Post请求
     *
     * @param action     网络请求尾址
     * @param params     请求参数
//     * @param clazz      返回数据类
//     * @param tag        请求复用时的判断标签
//     * @param showDialog 是否展示请求加载框
     */
    public<T extends BaseBean> void post(final String action, Map<String, Object> params) {

//        if (showDialog) {
//            showLoadDialog();
//        }

        if (params == null) {
            params = new HashMap<>();
        }
        if(!TextUtils.isEmpty(com.andlei.utils.SPUtils.getInstance(mContext).getString("Token"))){
            params.put("token", com.andlei.utils.SPUtils.getInstance(mContext).getString("Token"));
            params.put("manager_id", com.andlei.utils.SPUtils.getInstance(mContext).getInt("manager_id"));
        }else {
           // params.put("token",SPUtils.getInstance(mContext).getString("token"));
           // Toast.makeText(mContext,"用户未登录，请重新登录",Toast.LENGTH_SHORT).show();
        }
        String requestString = String.valueOf(new JSONObject(params));
        if (TextUtils.isEmpty(requestString) && com.andlei.utils.Const.notEmpty(netRequestCallBack)) {
            //noinspection unchecked
            netRequestCallBack.error(action, new Exception(com.andlei.utils.TxtUtils.getText(mContext, R.string.data_abnormal)), null);
        }

        com.andlei.netWork.RetrofitPostService jsonService = initBaseData(action).create(com.andlei.netWork.RetrofitPostService.class);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("from-data; charset=utf-8"),
                        requestString);

        com.andlei.utils.Logger.i("zzz", "request====" + requestString);

        jsonService.postResult(action.substring(action.lastIndexOf("/") + 1), params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<>(action, BaseBean.class, null, false));
    }

    /**
     * Post请求
     *
     * @param action     网络请求尾址
     * @param o          请求参数类
     * @param clazz      返回数据类
     * @param tag        请求复用时的判断标签
     * @param showDialog 是否展示请求加载框
     */
    public<T extends BaseBean> void post(final String action, Object o, Class<T> clazz, Map<String, ?> tag, boolean showDialog) {

        if (showDialog) {
            showLoadDialog();
        }

        String requestString = new Gson().toJson(o);
        if (TextUtils.isEmpty(requestString) && com.andlei.utils.Const.notEmpty(netRequestCallBack)) {
            //noinspection unchecked
            netRequestCallBack.error(action, new Exception(com.andlei.utils.TxtUtils.getText(mContext, R.string.data_abnormal)), tag);
        }

        com.andlei.netWork.RetrofitPostJsonService jsonService = initBaseData(action).create(com.andlei.netWork.RetrofitPostJsonService.class);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        requestString);

        com.andlei.utils.Logger.i("zzz", "request====" + requestString);

        jsonService.postResult(action.substring(action.lastIndexOf("/") + 1), requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<>(action, clazz, tag, showDialog));
    }

    private class MyObserver<T extends BaseBean> implements Observer<ResponseBody> {

        private Class<T> clazz;
        private String action;
        boolean showDialog;
        /**
         * 返回结果状态：0、正常Bean；1、Bitmap
         */
        private int resultStatus = 0;
        private Map<String, ?> tag;

        MyObserver(String action, Class<T> clazz, Map<String, ?> tag, boolean showDialog) {
            this.clazz = clazz;
            this.action = action;
            this.tag = tag;
            this.showDialog = showDialog;
        }

        MyObserver(String action, int resultStatus) {
            this.action = action;
            this.resultStatus = resultStatus;
        }

        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @SuppressWarnings("unchecked")
        @Override
        public void onNext(@NonNull ResponseBody responseBody) {
            if (showDialog) {
                hideLoadDialog();
            }
            try {
                switch (resultStatus) {
                    case 0:
                        String responseString = responseBody.string();
                        com.andlei.utils.Logger.i("responseString", action + "********** responseString get  " + responseString);
                        if (com.andlei.utils.Const.notEmpty(netRequestCallBack)) {
                            try {
                                netRequestCallBack.success(action, (T) new Gson().fromJson(responseString, clazz), tag);

                                BaseBean baseBean = new Gson().fromJson(responseString, clazz);
                                if(baseBean.ok == 100){
//                                        Intent intent = new Intent(mContext, LoginActivity.class);
//                                        mContext.startActivity(intent);
                                        return;
                                }

                            }catch (Exception e){
                                Toast.makeText(mContext,"接口返回数据异常！",Toast.LENGTH_LONG).show();
                                com.andlei.utils.Logger.d("数据解析异常",e+"");
                            }

                        }
                        break;
                    case 1:
                        if (com.andlei.utils.Const.notEmpty(netRequestCallBack)) {
                            netRequestCallBack.success(action, BitmapFactory.decodeStream(responseBody.byteStream()), tag);
                        }
                        com.andlei.utils.Logger.i("responseString", action + "********** 图片获取成功 ");
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onError(@NonNull Throwable e) {
            if (showDialog) {
                hideLoadDialog();
            }
            try {
                if (e instanceof HttpException) {
                    ResponseBody errorbody = ((HttpException) e).response().errorBody();
                    if (com.andlei.utils.Const.notEmpty(errorbody)) {
                        com.andlei.utils.Logger.i("responseString", String.format("%s********** responseString get error %s content %s", action, e.toString(), TextUtils.isEmpty(errorbody.string()) ? "" : errorbody));
                    }
                } else {
                    com.andlei.utils.Logger.i("responseString", String.format("%s********** responseString get error %s", action, e.toString()));
                }
            } catch (IOException | NullPointerException e1) {
                e1.printStackTrace();
            }
            if (com.andlei.utils.Const.notEmpty(netRequestCallBack)) {
                netRequestCallBack.error(action, e, tag);
            }
        }

        @Override
        public void onComplete() {

        }
    }

    /**
     * 隐藏加载提示框
     */
    public void hideLoadDialog() {
//        ((Activity) mContext).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (customProgressDialog != null && customProgressDialog.isShowing()) {
//                    customProgressDialog.dismiss();
//                }
//            }
//        });
    }

    /**
     * 显示加载提示框
     */
    public void showLoadDialog() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
//                    if (customProgressDialog != null && ! customProgressDialog.isShowing()) {
//                        customProgressDialog.show();
//                    }
                } catch (Exception ex) {
                    StringWriter stackTrace = new StringWriter();
                    ex.printStackTrace(new PrintWriter(stackTrace));
                    com.andlei.utils.Logger.i("Exception", stackTrace.toString());
                }
            }
        });
    }

    /**
     * 网络请求文本结果回调接口
     */
    public abstract static class NetRequestCallBack<TT extends BaseBean> {

        public void success(String action, Bitmap bitmap, Map<String, ?> tag) {

        }

        public void success(String action, File file, Map<String, ?> tag) {

        }

        public abstract void success(String action, TT t, Map<String, ?> tag);

        /**
         * 访问失败回调抽象方法
         *
         * @param action 网络访问尾址
         * @param e      所返回的异常
         * @param tag    当接口复用时，用于区分请求的表识
         */
        public abstract void error(String action, Throwable e, Map<String, ?> tag);
    }
}
