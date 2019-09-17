package adapter;

import android.support.annotation.Nullable;
import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import bean.SchoolDistributionBean;
import utils.TxtUtils;
import widget.LongClickButton;

/**
 * @author Andlei
 * @date 2019/8/27.
 */
public class SelectBuildingAdapter extends BaseQuickAdapter<SchoolDistributionBean.BuildingListBean,BaseViewHolder> {

    public SelectBuildingAdapter(int layoutResId, @Nullable List<SchoolDistributionBean.BuildingListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(final BaseViewHolder helper, final SchoolDistributionBean.BuildingListBean item) {

        helper.setText(R.id.tv_building,item.getName());
        helper.setText(R.id.tv_money,item.b_money);
        LongClickButton img_jia = helper.getView(R.id.img_jia);
        LongClickButton img_jian = helper.getView(R.id.img_jian);
        helper.addOnClickListener(R.id.img_jian);
        helper.addOnClickListener(R.id.img_jia);

        img_jia.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
           @Override
           public void repeatAction() {
               item.b_money = "" + TxtUtils.setDoubleScale((Double.parseDouble(item.b_money) + 0.1), 1);
               helper.setText(R.id.tv_money,item.b_money);
               //notifyDataSetChanged();
           }
        });
        img_jian.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                if (Double.parseDouble(item.b_money) <= 0.1) {
                    return;
                }
                item.b_money = "" + TxtUtils.setDoubleScale((Double.parseDouble(item.b_money) - 0.1), 1);
                helper.setText(R.id.tv_money,item.b_money);
            }
        });

    }
}
