package adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Switch;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.StoreBean;

/**
 * 菜品适配器
 * @author Andlei
 * @date 2019/6/24.
 */
public class GoodsOnOffListAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public GoodsOnOffListAdapter(int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {

        Switch aSwitch = helper.getView(R.id.switch_tab);
        helper.setText(R.id.tv_text_goods,item.getShop_name());
        if(item.getIs_open() == 1){
            aSwitch.setChecked(true);
        }else {
            aSwitch.setChecked(false);
        }
        helper.addOnClickListener(R.id.switch_tab);
    }
}
