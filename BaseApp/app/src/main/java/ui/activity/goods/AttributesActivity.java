package ui.activity.goods;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.andlei.baseapp.R;
import java.util.ArrayList;
import adapter.MostAttrAdapter;
import bean.GoodsDetailsEntity;
import utils.TextUtils;

/**
 * 商品属性管理
 * @author Andlei
 * @date 2019/9/16.
 */
public class AttributesActivity extends activity.BaseLayoutActivity {
    private RecyclerView recycler_view;
    private TextView tv_add;
    private MostAttrAdapter adapter;
    private ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> attrBeanList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("属性管理",true);
        setBaseRightText("保存");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attributes;
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
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MostAttrAdapter(attrBeanList);
        recycler_view.setAdapter(adapter);
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.base_right_text:
                // TODO 保存列表

                break;
            case R.id.tv_add:
                if (attrBeanList.size()>=5) {
                    TextUtils.makeText("最多添加5个属性");
                    return;
                }
                attrBeanList.add(new GoodsDetailsEntity.GoodsDetailBean.AttrBean());
                adapter.notifyDataSetChanged();
                recycler_view.smoothScrollToPosition(attrBeanList.size()-1);
                break;
        }
    }
}
