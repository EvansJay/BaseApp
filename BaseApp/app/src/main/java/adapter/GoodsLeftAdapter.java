package adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import bean.CategoryEntity;

public class GoodsLeftAdapter extends BaseQuickAdapter<CategoryEntity.CategoryListBean, BaseViewHolder> {
    public GoodsLeftAdapter(int layoutResId, @Nullable List<CategoryEntity.CategoryListBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CategoryEntity.CategoryListBean item) {
        helper.setText(R.id.tv_class_name,item.getName());
        if(item.isSelect){
            helper.getView(R.id.view_color).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_content).setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            helper.getView(R.id.view_color).setVisibility(View.INVISIBLE);
            helper.getView(R.id.ll_content).setBackgroundColor(Color.parseColor("#f0f0f0"));
        }
    }


}
