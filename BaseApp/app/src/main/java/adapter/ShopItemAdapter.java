package adapter;

import android.support.annotation.Nullable;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import bean.StoreBean;

/**
 *
 * @author Andlei
 * @date 2019/9/10.
 */
public class ShopItemAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public ShopItemAdapter(int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {
        helper.setText(R.id.tv_shopname,item.getShop_name());
    }
}
