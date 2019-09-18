package ui.activity.goods;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andlei.baseapp.R;

import java.util.ArrayList;

import adapter.MostSpecAdapter;
import adapter.OnRecyclerViewListener;
import bean.GoodsDetailsEntity;
import utils.TextUtils;
import widget.ScrollSpeedLinearLayoutManger;

/**
 * 多规格管理
 * @author Andlei
 * @date 2019/9/16.
 */
public class MostSpecActivity extends activity.BaseLayoutActivity {
    private RecyclerView recycler_view;
    private ArrayList<GoodsDetailsEntity.GoodsDetailBean.SpenBean> spenBeanList = new ArrayList<>();
    private TextView tv_add;
    private MostSpecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("多规格管理",true);
        setBaseRightText("保存");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mostspec;
    }

    @Override
    protected void findViews() {
        recycler_view = getView(R.id.recycler_view);
        tv_add = getView(R.id.tv_add);
    }

    @Override
    protected void formatViews() {
        tv_add.setOnClickListener(this);
    }

    @Override
    protected void formatData() {
        recycler_view.setLayoutManager(new ScrollSpeedLinearLayoutManger(this));

        adapter = new MostSpecAdapter(MostSpecActivity.this,spenBeanList);
        recycler_view.setAdapter(adapter);
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add:
                if (spenBeanList.size() >= 6) {
                   TextUtils.makeText("最多可以添加6个规格");
                    return;
                }
                spenBeanList.add(new GoodsDetailsEntity.GoodsDetailBean.SpenBean());
                adapter.notifyDataSetChanged();
                recycler_view.smoothScrollToPosition(spenBeanList.size() - 1);
                break;
            case R.id.base_right_text:
                // TODO  保存多规格列表

                break;
        }
    }
}
