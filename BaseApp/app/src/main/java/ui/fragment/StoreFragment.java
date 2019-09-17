package ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.andlei.baseapp.R;

import java.util.Map;

import base.BaseBean;
import base.fragment.BaseFragment;
import netWork.NetUtils;
import utils.Logger;

/**
 * 商户-fragment
 *
 */
public class StoreFragment extends BaseFragment {

//    private TextView textFragment;

    private NetUtils netUtils;

    public static StoreFragment getInstance() {
        return new StoreFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        //设置title文本
        setTitle("商户",false);

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
        return R.layout.fragment_mine_2;
    }

    @Override
    protected void findViews() {
//        textFragment = getView(R.id.text_fragment);
    }

    @Override
    protected void formatViews() {
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

    }

    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean t, Map tag) {
            Logger.e("dadadadad", action);
        }

        @Override
        public void error(String action, Throwable e, Map tag) {

        }
    };
}
