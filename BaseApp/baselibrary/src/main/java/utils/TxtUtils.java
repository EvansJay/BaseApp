 package com.andlei.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 《一个Android工程的从零开始》
 *
 * @author 半寿翁
 * @博客：
 * @CSDN http://blog.csdn.net/u010513377/article/details/74455960
 * @简书 http://www.jianshu.com/p/1410051701fe
 */
public class TxtUtils {
    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getText(TextView textText) {
        return textText.getText().toString().trim();
    }

    public static String getText(String text) {
        return TextUtils.isEmpty(text) ? "-" : text;
    }

    public static String getText(int text) {
        return TextUtils.isEmpty(String.format(Locale.CHINA, "%d", text)) ? "-" : String.format(Locale.CHINA, "%d", text);
    }

    public static String getText(long text) {
        return TextUtils.isEmpty(String.format(Locale.CHINA, "%d", text)) ? "-" : String.format(Locale.CHINA, "%d", text);
    }

    public static String getText(double text) {
        return TextUtils.isEmpty(String.format("%s", text)) ? "-" : String.format("%s", text);
    }

    public static String getText(Context context, int resourceId) {
        return context.getResources().getString(resourceId);
    }

    public static double setDoubleScale(double formatDouble, int scale) {
        /*
         * ROUND_HALF_UP，最后一位四舍五入
         */
        return new BigDecimal(formatDouble).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
