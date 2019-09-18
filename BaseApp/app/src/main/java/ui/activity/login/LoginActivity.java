package ui.activity.login;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.andlei.baseapp.R;
import com.andlei.baseapp.databinding.ActivityLoginBinding;
import java.util.HashMap;
import java.util.Map;
import base.BaseBean;
import netWork.NetUtils;
import ui.MainActivity;


/**
 * 登录页面
 *
 * @author Andlei
 * @DATE 2019/1/17
 */
public class LoginActivity extends activity.BaseLayoutActivity {
    private ActivityLoginBinding mBinding;
    private NetUtils netUtils;
    private boolean isChecked;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mBinding.setPage(this);
        netUtils = new NetUtils(LoginActivity.this, netRequestCallBack);
    }

    @Override
    protected void formatViews() {
       // mBinding.btCode.setOnClickListener(this);
        mBinding.btnLogin.setOnClickListener(this);
//        mBinding.tvXieyi.setOnClickListener(this);
//        mBinding.ivShowPwd.setOnClickListener(this);
//        mBinding.checkboxAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(compoundButton.isChecked()){
//                    mBinding.btnLogin.setBackgroundResource(R.drawable.bt_click);
//                    mBinding.btnLogin.setEnabled(true);
//                }else {
//                    mBinding.btnLogin.setBackgroundResource(R.color.bg_color);
//                    mBinding.btnLogin.setEnabled(false);
//                }
//            }
//        });
    }

    @Override
    protected void formatData() {
        if(!TextUtils.isEmpty(com.andlei.utils.SPUtils.getInstance(mActivity).getString("phone"))){
            mBinding.etName.setText(com.andlei.utils.SPUtils.getInstance(mActivity).getString("phone"));
        }
//        if(!TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("pwd"))){
//            mBinding.etPwd.setText(SPUtils.getInstance(mActivity).getString("pwd"));
//        }
    }

    @Override
    protected void getBundle(Bundle bundle) {
        //6.0权限处理
//        Acp.getInstance(App.getInstance()).request(new AcpOptions.Builder().setPermissions(
//                Manifest.permission.READ_PHONE_STATE).build(), new AcpListener() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onGranted() {
//            }
//            @Override
//            public void onDenied(List<String> permissions) {
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.bt_code:
//                //发送验证码
//                if(mBinding.btCode.getText().equals("获取验证码")){
//                    Map<String,Object> map = new HashMap<>();
//                    map.put("mobile",mBinding.etName.getText().toString().trim());
//                    netUtils.post("rider/user/phone-code",map, BaseBean.class,null,true);
//                }
//                break;
//            case R.id.iv_show_pwd:
//                if (isChecked){
//                    mBinding.ivShowPwd.setImageResource(R.mipmap.icon_biyan);
//                    mBinding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    isChecked = false;
//                }else {
//                    mBinding.ivShowPwd.setImageResource(R.mipmap.icon_zhengyan);
//                    mBinding.etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    isChecked = true;
//                }
                //break;
            case R.id.btn_login:
                if(TextUtils.isEmpty(mBinding.etName.getText().toString())){
                    toast("请输入手机号码");
                    return;
                }
                if(TextUtils.isEmpty(mBinding.etPwd.getText().toString())){
                    toast("请输入密码");
                    return;
                }
                //登录
                Map<String,Object> map = new HashMap<>();
                map.put("account",mBinding.etName.getText().toString().trim());
                map.put("pwd",mBinding.etPwd.getText().toString().trim());
                //map.put("reg_ua",DeviceUtil.getImei(mBinding.etName.getText().toString().trim()));
                netUtils.post("changestoresort/login",map);
                mBinding.btnLogin.setEnabled(false);
                break;
//            case R.id.tv_xieyi:
//                //跳转至协议
//                jumpTo(ServiceAgreementActivity.class);
//                break;
        }
    }
    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean baseBean, Map tag) {
            switch (action) {
//                case "rider/user/phone-code":
//                    if(baseBean.ok == 1){
//                        /** 倒计时60秒，一次1秒 */
//
//                    }else {
//                        toast(baseBean.getMsg());
//                    }
//                    break;
                case "changestoresort/login":
                    if(baseBean.ok == 1){
                        com.andlei.utils.SPUtils.getInstance(mContext).put("Token",baseBean.token);
                        com.andlei.utils.SPUtils.getInstance(mContext).put("manager_id",baseBean.manager_id);
                        com.andlei.utils.SPUtils.getInstance(mContext).put("phone",mBinding.etName.getText().toString());
                       // SPUtils.getInstance(mContext).put("pwd",mBinding.etPwd.getText().toString());
                        jumpTo(MainActivity.class);
                        finish();
                    }else {
                        toast(baseBean.getMsg());
                    }
                    mBinding.btnLogin.setEnabled(true);
                    break;

            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
            mBinding.btnLogin.setEnabled(true);
                if(action.equals("rider/user/register")){

                }
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }
}
