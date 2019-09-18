package adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

import bean.GoodsRightEntity;
import utils.GlideUtils;

public class GoodsRightAdapter extends BaseQuickAdapter<GoodsRightEntity.GoodsListBean, BaseViewHolder> {


    public GoodsRightAdapter(int layoutResId, @Nullable List<GoodsRightEntity.GoodsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsRightEntity.GoodsListBean item) {
        helper.setText(R.id.tv_goods_name,item.getName());
        GlideUtils.loadImageView(mContext,item.getIcon(), (ImageView) helper.getView(R.id.iv_goods_logo));
        helper.setText(R.id.tv_goods_month_num,"月售"+item.getBuy_num()+"份");
        helper.setText(R.id.tv_goods_stock,"库存"+item.getNum()+"份");
        helper.setText(R.id.tv_money,"¥" +item.getPrice());
        TextView view = helper.getView(R.id.btn_up_down);
        if(item.getStatus() == 1){
            view.setText("下架");
            view.setBackgroundResource(R.drawable.button_bg_border);
            view.setTextColor(mContext.getResources().getColor(R.color.txt_color5));
        }else {
            view.setText("上架");
            view.setBackgroundResource(R.drawable.button_bg_border2);
            view.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        helper.addOnClickListener(R.id.btn_edit);
        helper.addOnClickListener(R.id.btn_up_down);
    }


}
