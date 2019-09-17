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
 * @author Andlei
 * @date 2019/6/24.
 */
public class StoreOnOffListAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public StoreOnOffListAdapter(int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {

        Switch aSwitch = helper.getView(R.id.switch_tab);
        ImageView imageView = helper.getView(R.id.img_select);
        helper.setText(R.id.tv_text,item.getShop_name());
        helper.setText(R.id.tv_text2,"  ("+item.getDesc()+")");
        if(item.getIs_open() == 1){
            aSwitch.setChecked(true);
        }else {
            aSwitch.setChecked(false);
        }
        if(item.isSelect){
            imageView.setImageResource(R.mipmap.icon_is_select);
        }else {
            imageView.setImageResource(R.mipmap.icon_no_select);
        }
        helper.addOnClickListener(R.id.img_select);
        helper.addOnClickListener(R.id.switch_tab);
    }
}
