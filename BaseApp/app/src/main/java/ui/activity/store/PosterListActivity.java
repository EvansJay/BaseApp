package ui.activity.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import adapter.PosterSortRightAdapter;
import base.BaseBean;
import bean.StoreBean;
import netWork.NetUtils;
import utils.GsonUtils;
import widget.HiDialog;

/**
 * 超值抢购列表
 * @author Andlei
 * @date 2019/9/9.
 */
public class PosterListActivity extends activity.BaseLayoutActivity {
    private Button btn_sure;
    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private PosterSortRightAdapter mGoodsRightAdapter;
    private List<StoreBean> goodsList = new ArrayList<>();
    private boolean isUpdate;
    private NetUtils netUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("超值抢购列表",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_posterlist;
    }

    @Override
    protected void findViews() {
        swipeMenuRecyclerView = getView(R.id.recycler_view_right);
        btn_sure = getView(R.id.btn_sure);
        netUtils = new NetUtils(mActivity,netRequestCallBack);
    }

    @Override
    protected void formatViews() {
        btn_sure.setOnClickListener(this);

    }

    @Override
    protected void formatData() {
        for (int i = 0;i<20;i++){
            StoreBean bean = new StoreBean();
            bean.setShop_name("商品"+i);
            goodsList.add(bean);
        }
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setOnItemMoveListener(onItemMoveListener);
        mGoodsRightAdapter = new PosterSortRightAdapter(R.layout.item_postersort_right_class,goodsList,swipeMenuRecyclerView);
        swipeMenuRecyclerView.setAdapter(mGoodsRightAdapter);

        mGoodsRightAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()){
                    case R.id.tv_delete:
                        new HiDialog.Builder(PosterListActivity.this)
                                .setContent("确认要删除吗？")
                                .setLeftBtnText("取消")
                                .setRightBtnText("确定")
                                .setRightCallBack(new HiDialog.RightClickCallBack() {
                                    @Override
                                    public void dialogRightBtnClick() {
                                        //删除选项
                                        goodsList.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }).build();

                        break;
                    case R.id.switch_tab:
                        int i = 0;
                        for (StoreBean bean : goodsList) {
                            if (bean.getIs_open() == 1) {
                                i++;
                            }
                        }
                        if (goodsList.get(position).getIs_open() == 1) {
                            goodsList.get(position).setIs_open(0);
                        } else {
                            if (i >= 3) {
                                toast("最多开启三个首页广告推荐位");
                            } else {
                                goodsList.get(position).setIs_open(1);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }
    private void loadData(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", id + "");
        map.put("status","0");
        map.put("type",0+"");
        netUtils.post("changestoresort/get_store_list", map);
    }
    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();

            // Item被拖拽时，交换数据，并更新adapter。
            Collections.swap(goodsList, fromPosition, toPosition);
            mGoodsRightAdapter.notifyItemMoved(fromPosition, toPosition);
            isUpdate = true;
            //setSortGoods(getListDate(goodsList));
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            goodsList.remove(position);
            mGoodsRightAdapter.notifyItemRemoved(position);
        }

    };
    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean baseBean, Map tag) {
            switch (action) {
                case "changestoresort/get_store_list":
                    goodsList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean, StoreBean.class);
                    Comparator<StoreBean> netTypeComparator = new Comparator<StoreBean>() {
                        @Override
                        public int compare(StoreBean o1, StoreBean o2) {
                            return o1.sort - o2.sort;
                        }
                    };

                    Collections.sort(goodsList,netTypeComparator);
                    //mGoodsRightAdapter = new GoodsSortRightAdapter(goodsList, swipeMenuRecyclerView);
                    //swipeMenuRecyclerView.setAdapter(mGoodsRightAdapter);
                    mGoodsRightAdapter.notifyDataSetChanged();
                    break;
                case "changestoresort/update_store_sort":
                    if (baseBean.ok == 1) {
                        toast("保存成功");
                        //loadData(School_id);
                        finish();
                    }else {
                        toast(baseBean.getMsg());
                    }

            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
            toast(""+e);
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sure:
                mGoodsRightAdapter.notifyDataSetChanged();

                break;
        }
    }
}
