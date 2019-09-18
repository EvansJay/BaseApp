package ui.activity.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.GoodsItemAdapter;
import adapter.ShopItemAdapter;


import base.BaseBean;
import bean.StoreBean;
import netWork.NetUtils;
import utils.GsonUtils;

import widget.ClearEditText;
import widget.HiDialog;

/**
 * 添加菜品
 *
 * @author Andlei
 * @date 2019/9/9.
 */
public class AddingDishesActivity extends activity.BaseLayoutActivity {
    private TextView tv_shopname;
    private ClearEditText edittext_search_shopname, edittext_search_dishes;
    private RecyclerView recy_list, recy_list_goods;
    private NetUtils netUtils;
    private Button btn_save;
    private String store_id;
    private boolean isUpdate;
    private List<StoreBean> finalstoreList = new ArrayList<>();
    private List<BaseBean> finalgoodsList = new ArrayList<>();
    private List<StoreBean> storeList = new ArrayList<>();
    private List<BaseBean> goodsList = new ArrayList<>();
    private String School_id;
    private ShopItemAdapter adapter;
    private GoodsItemAdapter goodsItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加菜品", true);
        resetBaseBack();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adding_dishes;
    }

    @Override
    protected void findViews() {
        netUtils = new NetUtils(mActivity, netRequestCallBack);
        tv_shopname = getView(R.id.tv_shopname);
        edittext_search_shopname = getView(R.id.edittext_search_shopname);
        edittext_search_dishes = getView(R.id.edittext_search_dishes);
        recy_list = getView(R.id.recy_list);
        recy_list_goods = getView(R.id.recy_list_goods);
        btn_save = getView(R.id.btn_save);
    }

    @Override
    protected void formatViews() {
        recy_list.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ShopItemAdapter(R.layout.item_shop_, storeList);
        recy_list.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                store_id = storeList.get(position).getId();
                tv_shopname.setText(storeList.get(position).getShop_name());
                edittext_search_shopname.setText("");
            }
        });

        for (int i = 0; i < 10; i++) {
            BaseBean bean = new BaseBean();
            bean.setMsg("商品" + i);
            goodsList.add(bean);
            finalgoodsList.add(bean);
        }
        recy_list_goods.setLayoutManager(new LinearLayoutManager(mContext));
        goodsItemAdapter = new GoodsItemAdapter(R.layout.item_goods_, goodsList);
        recy_list_goods.setAdapter(goodsItemAdapter);
        goodsItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goodsList.get(position).Select = !goodsList.get(position).Select;
                adapter.notifyDataSetChanged();
                isUpdate = true;
            }
        });
        edittext_search_shopname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                storeList.clear();
                if (!TextUtils.isEmpty(editable.toString())) {
                    for (StoreBean bean : finalstoreList) {
                        if (bean.getShop_name().contains(editable.toString())) {
                            storeList.add(bean);
                        }
                    }
                }
                adapter.setNewData(storeList);
            }
        });

        edittext_search_dishes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                goodsList.clear();
                if (!TextUtils.isEmpty(editable.toString())) {
                    for (BaseBean bean : finalgoodsList) {
                        if (bean.getMsg().contains(editable.toString())) {
                            goodsList.add(bean);
                        }
                    }
                } else {
                    goodsList.addAll(finalgoodsList);
                }
                goodsItemAdapter.setNewData(goodsList);
            }
        });
    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", School_id + "");
        map.put("status", "0");
        map.put("type", "0");
        netUtils.post("changestoresort/get_store_list", map);
    }

    @Override
    protected void formatData() {
        loadData();
    }

    @Override
    protected void getBundle(Bundle bundle) {
        School_id = bundle.getString("school_id");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_back:
                if (isUpdate) {
                    new HiDialog.Builder(AddingDishesActivity.this)
                            .setContent("要保存设置再退出吗？")
                            .setLeftBtnText("取消")
                            .setRightBtnText("确定")
                            .setLeftCallBack(new HiDialog.LeftClickCallBack() {
                                @Override
                                public void dialogLeftBtnClick() {
                                    finish();
                                }
                            })
                            .setRightCallBack(new HiDialog.RightClickCallBack() {
                                @Override
                                public void dialogRightBtnClick() {
                                    // TODO 调用保存接口

                                }
                            }).build();
                } else {
                    finish();
                }
                break;
            case R.id.btn_save:
                toast("已保存");
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (isUpdate) {
            new HiDialog.Builder(AddingDishesActivity.this)
                    .setContent("要保存设置再退出吗？")
                    .setLeftBtnText("取消")
                    .setRightBtnText("确定")
                    .setLeftCallBack(new HiDialog.LeftClickCallBack() {
                        @Override
                        public void dialogLeftBtnClick() {
                            finish();
                        }
                    })
                    .setRightCallBack(new HiDialog.RightClickCallBack() {
                        @Override
                        public void dialogRightBtnClick() {
                            // TODO 调用保存接口

                        }
                    }).build();

        } else {
            finish();
        }
    }

    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {

        @Override
        public void error(String action, Throwable e, Map tag) {
            toast(e + "");
        }

        @Override
        public void success(String action, BaseBean t, Map tag) {
            switch (action) {
                case "changestoresort/get_store_list":
                    finalstoreList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t, StoreBean.class);

                    break;
            }
        }
    };
}
