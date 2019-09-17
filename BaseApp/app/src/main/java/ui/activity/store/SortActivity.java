package ui.activity.store;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.GoodsSortRightAdapter;
import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.DouBanBean;
import bean.GoodsRightEntity;
import bean.SchoolBean;
import bean.SortPlanBean;
import bean.SortTypeBean;
import bean.StoreBean;
import netWork.NetUtils;
import ui.activity.rider.DistributionSettingActivity;
import utils.GsonUtils;
import utils.JumpToUtils;
import utils.Logger;
import utils.SPUtils;
import widget.ClearEditText;
import widget.HiDialog;

/**
 * 店铺排序
 */
public class SortActivity extends BaseLayoutActivity {
    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private GoodsSortRightAdapter mGoodsRightAdapter;
    //    private Spinner spinner,spinner_fun;
    private ClearEditText editText;
    private List<StoreBean> goodsList = new ArrayList<>();
    private NetUtils netUtils;
    List<SchoolBean> schoolBeanList = new ArrayList<>();
    private List<SortPlanBean> planBeanList = new ArrayList<>();
    private String School_id;
    private int type = 1;
    private String start_time = "开始时间"
            ,end_time = "结束时间";
    private String title;
    private RelativeLayout Rlayout_time_select;
    private TextView tv_start_time, tv_end_time;
    private ClearEditText edittext_search;
    private boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(title + "排序", true);
        setBaseRightText("保存");
        setOnClickListener(R.id.base_back);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort;
    }

    @Override
    protected void findViews() {
        netUtils = new NetUtils(mContext, netRequestCallBack);
        Rlayout_time_select = getView(R.id.Rlayout_time_select);
        tv_start_time = getView(R.id.tv_start_time);
        tv_end_time = getView(R.id.tv_end_time);
        edittext_search = getView(R.id.edittext_search);
        swipeMenuRecyclerView = getView(R.id.recycler_view_right);
//        spinner = getView(R.id.spinner);
//        spinner_fun = getView(R.id.spinner_fun);
        netUtils.post("changestoresort/get_school_list", null);
//        editText = getView(R.id.edittext_search);
    }

    @Override
    protected void formatViews() {
        if (title.equals("默认")) {
            Rlayout_time_select.setVisibility(View.GONE);
        } else {
            Rlayout_time_select.setVisibility(View.VISIBLE);
        }
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable.toString())){
                    for (int i = 0; i < goodsList.size(); i++) {
                        goodsList.get(i).isSelect = false;
                    }
                    mGoodsRightAdapter.notifyDataSetChanged();
                }
            }
        });
        edittext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    int position = 0;
                    for (int i = 0; i < goodsList.size(); i++) {
                        if (goodsList.get(i).getShop_name().contains(edittext_search.getText().toString().trim())) {
                            position = i;
                        }
                        goodsList.get(i).isSelect = false;
                    }
                    if(!TextUtils.isEmpty(edittext_search.getText().toString().trim())){
                        goodsList.get(position).isSelect = true;
                        swipeMenuRecyclerView.smoothScrollToPosition(position);
                    }
                    mGoodsRightAdapter.notifyDataSetChanged();


                }
                return false;
            }
        });
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeMenuRecyclerView.setOnItemMoveListener(onItemMoveListener);
        swipeMenuRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //JumpToUtils.JumpToNewApp(goodsList.get(position).getAccount(),goodsList.get(position).getPassword());

            }
        });
        loadData(School_id);


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

    @Override
    protected void formatData() {
        tv_start_time.setText(start_time);
        tv_end_time.setText(end_time);
    }

    @Override
    protected void onDestroy() {
        SPUtils.getInstance(mActivity).put("School_id", School_id);
        super.onDestroy();
    }

    @Override
    protected void getBundle(Bundle bundle) {
        title = bundle.getString("title");
        School_id = bundle.getString("School_id");
        type = bundle.getInt("type");
        if(!TextUtils.isEmpty(bundle.getString("start_time"))){
            start_time = bundle.getString("start_time");
        }
        if(!TextUtils.isEmpty(bundle.getString("end_time"))){
            end_time = bundle.getString("end_time");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_back:
                if (isUpdate) {
                    new HiDialog.Builder(SortActivity.this)
                            .setContent("要保存设置再退出吗？")
                            .setLeftBtnText("取消")
                            .setLeftCallBack(new HiDialog.LeftClickCallBack() {
                                @Override
                                public void dialogLeftBtnClick() {
                                    finish();
                                }
                            })
                            .setRightBtnText("确定")
                            .setRightCallBack(new HiDialog.RightClickCallBack() {
                                @Override
                                public void dialogRightBtnClick() {
                                    SaveData(School_id, goodsList);
                                }
                            }).build();
                } else {
                    finish();
                }
                break;
            case R.id.base_right_text:
                SaveData(School_id, goodsList);
                break;
        }
    }

    private void loadData(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", id + "");
        map.put("status","0");
        map.put("type",type+"");
        Logger.i("type--->",type+"");
        netUtils.post("changestoresort/get_store_list", map);
    }

    private void SaveData(String id, List<StoreBean> goodsList) {
        if (goodsList.size() <= 0) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", id + "");
        map.put("type", type);
        map.put("sort_list", getListDate(goodsList));
        netUtils.post("changestoresort/update_store_sort", map);
    }

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
                    mGoodsRightAdapter = new GoodsSortRightAdapter(goodsList, swipeMenuRecyclerView);
                    swipeMenuRecyclerView.setAdapter(mGoodsRightAdapter);
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

    private String getListDate(List<StoreBean> list) {
        StringBuffer sortInfo = new StringBuffer();

        sortInfo.append("[");
        for (int i = 0; i < list.size(); i++) {
            sortInfo.append("{");
            sortInfo.append("\"bind_id\":" + list.get(i).getBind_id() + ",");
            sortInfo.append("\"sort\":" + (i + 1));
            sortInfo.append("}");
            sortInfo.append(",");
        }
        sortInfo.deleteCharAt(sortInfo.length() - 1);
        sortInfo.append("]");

        return sortInfo.toString();
    }

    private Map<String, Object> objectMap(List<StoreBean> list) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            // map.put("",)
        }
        return map;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isUpdate) {
            new HiDialog.Builder(SortActivity.this)
                    .setContent("要保存设置再退出吗？")
                    .setLeftBtnText("取消")
                    .setLeftCallBack(new HiDialog.LeftClickCallBack() {
                        @Override
                        public void dialogLeftBtnClick() {
                            finish();
                        }
                    })
                    .setRightBtnText("确定")
                    .setRightCallBack(new HiDialog.RightClickCallBack() {
                        @Override
                        public void dialogRightBtnClick() {
                            SaveData(School_id, goodsList);
                        }
                    }).build();
        } else {
            finish();
        }
    }
}
