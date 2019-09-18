package adapter;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.List;
import bean.GoodsRightEntity;
import utils.GlideUtils;


public class GoodsNewSortRightAdapter extends BaseQuickAdapter<GoodsRightEntity.GoodsListBean, BaseViewHolder> {
    private List<GoodsRightEntity.GoodsListBean> mRightData;
    private SwipeMenuRecyclerView swipeMenuRecyclerView;

    public GoodsNewSortRightAdapter(int layoutResId, @Nullable List<GoodsRightEntity.GoodsListBean> data,SwipeMenuRecyclerView swipeMenuRecyclerView) {
        super(layoutResId, data);
        this.swipeMenuRecyclerView = swipeMenuRecyclerView;
    }

//    public GoodsNewSortRightAdapter(List<GoodsRightEntity.GoodsListBean> goodsList, SwipeMenuRecyclerView swipeMenuRecyclerView) {
//        this.mRightData = goodsList;
//        this.swipeMenuRecyclerView = swipeMenuRecyclerView;
//    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_right_class, parent, false));
//        viewHolder.swipeMenuRecyclerView = swipeMenuRecyclerView;
//        return viewHolder;
//    }

    @Override
    protected void convert(final BaseViewHolder helper, GoodsRightEntity.GoodsListBean item) {

        GlideUtils.loadImageView(mContext,item.getIcon(), (ImageView) helper.getView(R.id.iv_goods_logo));
        helper.setText(R.id.tv_goods_name,item.getName());
        helper.setText(R.id.tv_goods_month_num,"月售"+item.getBuy_num()+"份");
        helper.setText(R.id.tv_money,"￥"+item.getPrice());
        helper.setOnTouchListener(R.id.iv_paixu, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        swipeMenuRecyclerView.startDrag(helper);
                        break;
                    }
                }
                return false;
            }
        });
        helper.addOnClickListener(R.id.tv_go_up);
    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//
//        if (holder instanceof ViewHolder) {
//            ViewHolder viewHolder = (ViewHolder) holder;
//            GoodsRightEntity.GoodsListBean entity = mRightData.get(position);
////            if(entity.getDisable()==1){
////                viewHolder.tv_goods_name.setText(entity.getShop_name()+"（休息中）");
////            }else {
////                viewHolder.tv_goods_name.setText(entity.getShop_name()+"（正在营业）");
////            }
////            if(mRightData.get(position).isSelect){
////                viewHolder.rlayout_main.setBackgroundResource(R.color.color_8DEFD0);
////            }else {
////                viewHolder.rlayout_main.setBackgroundResource(R.color.write);
////            }
//
//            viewHolder.tv_num.setText((position+1)+"");
////            Glide.with(viewHolder.itemView.getContext()).load(entity.getIcon()).into(viewHolder.iv_goods_logo);
////            viewHolder.tv_goods_month_num.setText("月售" + entity.getBuy_num() + "份");
////            viewHolder.tv_money.setText("¥" + entity.getPrice());
//
//
//
////            viewHolder.tv_go_up.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    StoreBean goodsListBean = mRightData.get(position);
////                    mRightData.remove(goodsListBean);
////                    mRightData.add(0,goodsListBean);
////                    notifyDataSetChanged();
////                }
////            });
//
//        }
//
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mRightData.size();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
////            case R.id.btn_edit:
////                if (mOnRecyclerViewListener != null) {
////                    mOnRecyclerViewListener.onItemClick(v,Integer.parseInt(String.valueOf(v.getTag())));
////                }
////                break;
////            case R.id.btn_up_down:
////                if (mOnRecyclerViewListener != null) {
////                    mOnRecyclerViewListener.onItemClick(v,Integer.parseInt(String.valueOf(v.getTag())));
////                }
////                break;
//            default:
//                break;
//        }
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
//        private final RelativeLayout rlayout_main;
//        private final TextView tv_goods_name;
//        private final ImageView iv_goods_logo;
//        private final TextView tv_goods_month_num;
//        private final TextView tv_money;
//        private final ImageView iv_paixu;
//        private final TextView tv_num;
//        private final TextView tv_go_up;
//        private SwipeMenuRecyclerView swipeMenuRecyclerView;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            rlayout_main = itemView.findViewById(R.id.rlayout_main);
//            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
//            iv_goods_logo = itemView.findViewById(R.id.iv_goods_logo);
//            tv_goods_month_num = itemView.findViewById(R.id.tv_goods_month_num);
//            tv_money = itemView.findViewById(R.id.tv_money);
//            tv_go_up = itemView.findViewById(R.id.tv_go_up);
//            iv_paixu = itemView.findViewById(R.id.iv_paixu);
//            tv_num = itemView.findViewById(R.id.tv_num);
//            iv_paixu.setOnTouchListener(this);
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            int action = event.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_DOWN: {
//                    swipeMenuRecyclerView.startDrag(this);
//                    break;
//                }
//            }
//            return false;
//        }
//    }
//
//
//
//    public OnRecyclerViewListener mOnRecyclerViewListener;
//    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
//        this.mOnRecyclerViewListener = l;
//    }
}
