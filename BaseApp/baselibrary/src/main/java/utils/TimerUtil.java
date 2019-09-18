package com.andlei.utils;

import android.os.CountDownTimer;
import android.support.annotation.IdRes;

/**
 * 《一个Android工程的从零开始》
 *
 * @author 半寿翁
 * @博客：
 * @CSDN http://blog.csdn.net/u010513377/article/details/74455960
 * @简书 http://www.jianshu.com/p/1410051701fe
 */
public class TimerUtil extends CountDownTimer {

    private OnBaseTimerCallBack onBaseTimerCallBack;
    private int viewId = 0;

    /**
     * @param viewId              需要定时器的ViewId
     * @param millisInFuture      时间间隔是多长时间
     * @param countDownInterval   回调onTick方法，每隔多久执行一次
     * @param onBaseTimerCallBack 回调方法
     */
    public TimerUtil(@IdRes int viewId, long millisInFuture, long countDownInterval, OnBaseTimerCallBack onBaseTimerCallBack) {
        super(millisInFuture, countDownInterval);
        this.viewId = viewId;
        this.onBaseTimerCallBack = onBaseTimerCallBack;
    }

    /**
     * @param millisInFuture      时间间隔是多长时间
     * @param countDownInterval   回调onTick方法，每隔多久执行一次
     * @param onBaseTimerCallBack 回调方法
     */
    public TimerUtil(long millisInFuture, long countDownInterval, OnBaseTimerCallBack onBaseTimerCallBack) {
        super(millisInFuture, countDownInterval);
        this.onBaseTimerCallBack = onBaseTimerCallBack;
    }

    //间隔时间内执行的操作
    @Override
    public void onTick(long millisUntilFinished) {
        onBaseTimerCallBack.onTick(viewId, millisUntilFinished);
    }

    //间隔时间结束的时候才会调用
    @Override
    public void onFinish() {
        onBaseTimerCallBack.onFinish(viewId);
    }

    /**
     * 图片二点击回调接口
     */
    public interface OnBaseTimerCallBack {
        /**
         * 间隔时间内执行的操作
         *
         * @param millisUntilFinished 距离结束剩余时间
         */
        void onTick(int viewId, long millisUntilFinished);

        /**
         * 间隔时间结束的时候才会调用
         */
        void onFinish(int viewId);
    }

    /*
     *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     * */
    public static boolean getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        Logger.i("diff--->",diff+"");
        if (diff>(3600*24*1000)) return true;
        else return false;
//            return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
//        if (hour != 0) return hour + "小时" + min + "分钟" + sec + "秒";
//        if (min != 0) return min + "分钟" + sec + "秒";
//        if (sec != 0) return sec + "秒";
//        return "0秒";
    }

}
