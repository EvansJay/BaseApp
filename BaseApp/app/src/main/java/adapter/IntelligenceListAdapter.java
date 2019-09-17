package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.StoreBean;

/**
 * @author Andlei
 * @date 2019/7/4.
 */
public class IntelligenceListAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public IntelligenceListAdapter(int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {

    }
}
