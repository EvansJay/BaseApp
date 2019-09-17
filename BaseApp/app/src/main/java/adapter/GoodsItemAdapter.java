package adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import base.BaseBean;

/**
 * @author Andlei
 * @date 2019/9/16.
 */
public class GoodsItemAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {
    public GoodsItemAdapter(int layoutResId, @Nullable List<BaseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.tv_dishes_name,item.msg);
        if(item.Select){
            helper.getView(R.id.img_gouxuan).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.img_gouxuan).setVisibility(View.GONE);
        }
    }
}
