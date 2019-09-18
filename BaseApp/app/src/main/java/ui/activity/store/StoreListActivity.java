package ui.activity.store;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.andlei.baseapp.R;



/**
 * 店铺列表
 * @author Andlei
 * @date 2019/9/11.
 */
public class StoreListActivity extends activity.BaseLayoutActivity {
    private RelativeLayout layout_onoff,layout_goods_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("店铺列表",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_list;
    }

    @Override
    protected void findViews() {
        layout_onoff = getView(R.id.layout_onoff);
        layout_goods_set = getView(R.id.layout_goods_set);
    }

    @Override
    protected void formatViews() {
        layout_onoff.setOnClickListener(this);
        layout_goods_set.setOnClickListener(this);
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_onoff:
                jumpTo(OnOffStoreActivity.class);
                break;
            case R.id.layout_goods_set:
                jumpTo(GoodsManagerActivity.class);
                break;
        }
    }
}
