package ui.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.andlei.baseapp.R;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import base.BaseBean;
import base.activity.BaseLayoutActivity;
import netWork.NetUtils;
import ui.MainActivity;
import utils.SPUtils;
import utils.TimerUtil;

/**
 * loaing加载页
 *
 * @author Andlei
 * @DATE 2019/1/17
 */
public class loadingActivity extends BaseLayoutActivity {
    private Timer timer;
    private NetUtils netUtil;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void findViews() {
        netUtil = new NetUtils(mActivity,netRequestCallBack);
    }

    @Override
    protected void formatViews() {
        timer = new Timer();
       // if(!TextUtils.isEmpty(SPUtils.getInstance(loadingActivity.this).getString("token"))){
            netUtil.get("changestoresort/get_time", null,BaseBean.class,null,false);
//        }else {
//            jumpTo(LoginActivity.class);
//      }

    }

    @Override
    protected void formatData() {

//        TimerUtil.getDistanceTime(1559732316,1559991516);
//        Logger.i("----->",""+TimerUtil.getDistanceTime(1559732316,1559991516));
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {

    }
    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void error(String action, Throwable e, Map tag) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    jumpTo(LoginActivity.class);
//                    if(!TextUtils.isEmpty(SPUtils.getInstance(loadingActivity.this).getString("time"))){
//                        if(TimerUtil.getDistanceTime(Long.parseLong(SPUtils.getInstance(loadingActivity.this).getString("time")),Long.parseLong(t.time))){
//                            SPUtils.getInstance(loadingActivity.this).put("time", "");
//                            jumpTo(LoginActivity.class);
//                        }else {
//                            jumpTo(SortActivity.class);
//                        }
//
//                    }else {
//                        SPUtils.getInstance(loadingActivity.this).put("time", t.getTime());
//                        jumpTo(LoginActivity.class);
//                    }
                    finish();
                }
            },1000);
        }

        @Override
        public void success(String action, final BaseBean t, Map tag) {
           // SPUtils.getInstance(loadingActivity.this).put("time", t.getTime());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!TextUtils.isEmpty(SPUtils.getInstance(loadingActivity.this).getString("time"))){

                        if(TimerUtil.getDistanceTime(Long.parseLong(SPUtils.getInstance(loadingActivity.this).getString("time")),Long.parseLong(t.time))){

                            SPUtils.getInstance(loadingActivity.this).put("time", "");
                            jumpTo(LoginActivity.class);
                        }else {
                            if(TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("Token"))){
                                jumpTo(LoginActivity.class);
                            }else {
                                jumpTo(MainActivity.class);
                            }
                        }

                    }else {
                        SPUtils.getInstance(loadingActivity.this).put("time", t.getTime());
                        jumpTo(LoginActivity.class);
                    }
                    finish();
                }
            },1000);
        }
    };
}
