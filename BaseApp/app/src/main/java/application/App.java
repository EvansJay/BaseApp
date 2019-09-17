package application;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import crash.CrashHandler;

/**
 *
 * @author Andlei
 */
public class App extends Application {
    private static App app;
    private static SharedPreferences preferences;
    public static String storageUrl = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

        //注册bugly
        Bugly.init(getApplicationContext(), "14e0d57f7f", false);
        //CrashReport.initCrashReport(getApplicationContext(), "14e0d57f7f", false);

        MultiDex.install(this);

    }

    public static App getInstance() {
        return app;
    }

    public static SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = app.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = preferences.edit();


        edit.putString("putString", "string").apply();

        edit.remove("putString").apply();

        edit.clear().apply();

        return preferences;

    }

    /**
     * 获取内部存储路径
     *
     * @return 路径
     */
    public String getDirPath() {
        return getApplicationContext().getFilesDir().getAbsolutePath();
    }

}
