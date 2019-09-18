package ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.andlei.baseapp.R;
import java.util.Map;
import base.BaseBean;
import base.fragment.BaseFragment;
import netWork.NetUtils;
import ui.activity.login.LoginActivity;
import widget.HiDialog;

/**
 * 我的 - fragment
 *
 */
public class MineFragment extends BaseFragment {

    private TextView tv_logout;
    private TextView tv_name;
    private TextView tv_version;
    private NetUtils netUtils;

    public static MineFragment getInstance() {
        return new MineFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        //设置title文本
        setTitle("我的",false);

        netUtils = new NetUtils(mContext, netRequestCallBack);
//        //设置返回拦截
//        setBaseBack(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jumpTo(TestActivity.class);
//            }
//        });
//        //设置功能键，以及点击方法回调监听
//        setBaseRightIcon1(R.mipmap.add, "更多", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                get("top250", BaseBean.class, false);
//            }
//        });
//        setBaseRightIcon2(R.mipmap.more, "更多", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                post("top250", BaseBean.class, false);
//            }
//        });
//        hideTitle();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (hidden) {
//            toast("隐藏了");
//        } else {
//            toast("显示了");
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void findViews() {
        tv_logout = getView(R.id.tv_logout);
        tv_name = getView(R.id.tv_name);
        tv_version = getView(R.id.tv_version);
//        textFragment = getView(R.id.text_fragment);
    }

    @Override
    protected void formatViews() {
        tv_logout.setOnClickListener(this);
        tv_name.setText("用户名："+ com.andlei.utils.SPUtils.getInstance(mContext).getString("phone"));
        tv_version.setText("版本V:"+ com.andlei.utils.APKVersionCodeUtils.getVerName(mActivity));
//        textFragment.setText("找到了，而且赋值成功");
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:
                new HiDialog.Builder(getActivity())
                        .setContent("确定要退出登录吗？")
                        .setLeftBtnText("取消")
                        .setRightBtnText("确定")
                        .setRightCallBack(new HiDialog.RightClickCallBack() {
                            @Override
                            public void dialogRightBtnClick() {
                                //退出登录
                                com.andlei.utils.SPUtils.getInstance(mActivity).put("Token","");
                                jumpTo(LoginActivity.class);
                                mActivity.finish();
                            }
                        }).build();

                break;
        }
    }

    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean t, Map tag) {
            com.andlei.utils.Logger.e("dadadadad", action);
        }

        @Override
        public void error(String action, Throwable e, Map tag) {

        }
    };
}
