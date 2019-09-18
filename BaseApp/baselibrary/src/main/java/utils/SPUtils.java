 package com.andlei.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * 《一个Android工程的从零开始》
 *
 * @author 半寿翁
 * @博客：
 * @CSDN http://blog.csdn.net/u010513377/article/details/74455960
 * @简书 http://www.jianshu.com/p/1410051701fe
 */

public class SPUtils {
    private SharedPreferences preferences = null;
    private static SPUtils utils;

    public SPUtils(Context context) {
        initPreferences(context);
    }

    public static SPUtils getInstance(Context context) {
        if (utils == null) {
            utils = new SPUtils(context);
        }
        return utils;
    }

    private void initPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getApplicationContext().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        }
    }

    public void put(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void put(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public void put(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void put(String key, Activity activity) {
        preferences.edit().putString(key, activity.getClass().getName()).apply();
    }

    public void put(String key, Class clazz) {
        preferences.edit().putString(key, clazz.getName()).apply();
    }

    public void put(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    public void put(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public void put(String key, Set<String> values) {
        preferences.edit().putStringSet(key, values).apply();
    }

    public String getString(String key, String defaultString) {
        return preferences.getString(key, defaultString);
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultBoolean) {
        return preferences.getBoolean(key, defaultBoolean);
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultInt) {
        return preferences.getInt(key, defaultInt);
    }

    public float getFloat(String key, float defaultFloat) {
        return preferences.getFloat(key, defaultFloat);
    }

    public Class getClass(String key) throws ClassNotFoundException {
        return Class.forName(key);
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public long getLong(String key, long defaultLong) {
        return preferences.getLong(key, defaultLong);
    }

    public Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, null);
    }

    public Set<String> getStringSet(String key, Set<String> defaultStringSet) {
        return preferences.getStringSet(key, defaultStringSet);
    }

    public Map<String, ?> getStringSet() {
        return preferences.getAll();
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    /*
获取公钥key的方法（读取.crt认证文件）
*/
//    private static String getKeyFromCRT(){
//        String key="";
//        CertificateFactory certificatefactory;
//        X509Certificate Cert;
//        InputStream bais;
//        PublicKey pk;
//        BASE64Encoder bse;
//        try{
//            //若此处不加参数 "BC" 会报异常：CertificateException - OpenSSLX509CertificateFactory$ParsingException
//            certificatefactory= CertificateFactory.getInstance("X.509","BC");
//            //读取放在项目中assets文件夹下的.crt文件；你可以读取绝对路径文件下的crt，返回一个InputStream（或其子类）即可。
//            bais = this.getAssets().open("xxx.crt");
//            Cert = (X509Certificate) certificatefactory.generateCertificate(bais);
//            pk = Cert.getPublicKey();
//            bse = new BASE64Encoder();
//            key=bse.encode(pk.getEncoded());
////            Log.e("源key-----"+ Cert.getPublicKey());
////            Log.e("加密key-----"+bse.encode(pk.getEncoded()));
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        key=key.replaceAll("\\n", "").trim();//去掉文件中的换行符
//        return key;
//    }

}
