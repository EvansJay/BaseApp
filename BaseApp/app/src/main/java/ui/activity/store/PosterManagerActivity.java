package ui.activity.store;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andlei.baseapp.R;

import base.activity.BaseLayoutActivity;

/**
 * 广告管理
 * @author Andlei
 * @date 2019/9/9.
 */
public class PosterManagerActivity extends BaseLayoutActivity {
    private Button btn_poster,btn_brands,btn_keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("广告管理",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poster;
    }

    @Override
    protected void findViews() {
        btn_poster = getView(R.id.btn_poster);
        btn_brands = getView(R.id.btn_brands);
        btn_keyword = getView(R.id.btn_keyword);
    }

    @Override
    protected void formatViews() {
        btn_poster.setOnClickListener(this);
        btn_brands.setOnClickListener(this);
        btn_keyword.setOnClickListener(this);
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
            case R.id.btn_poster:
                // todo 超值抢购
                jumpTo(OvervalueSnapUpActivity.class);
                break;
            case R.id.btn_brands:
                // todo 品牌推荐
                jumpTo(BrandsActivity.class);
                break;
            case R.id.btn_keyword:
                // todo 首页搜索关键词
                jumpTo(IndexSearchKeywordActivity.class);
                break;
        }
    }
}
