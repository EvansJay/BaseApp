package ui.activity.order;

import android.os.Bundle;
import android.view.View;

import com.andlei.baseapp.R;

import base.activity.BaseLayoutActivity;

/**
 * 订单列表
 * @author Andlei
 * @date 2019/7/2.
 */
public class OrderListActivity extends BaseLayoutActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("订单列表",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {

    }
}
