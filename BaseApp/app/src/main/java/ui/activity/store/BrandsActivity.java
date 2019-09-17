package ui.activity.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ShopItemAdapter;
import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.SchoolBean;
import bean.StoreBean;
import netWork.NetUtils;
import utils.GsonUtils;
import utils.SPUtils;
import widget.ClearEditText;
import widget.MaxHeightRecyclerView;

/**
 * 品牌推荐
 * @author Andlei
 * @date 2019/9/10.
 */
public class BrandsActivity extends BaseLayoutActivity {
    private Spinner spinner;
    private ClearEditText ed_shopname_,ed_shopname_1,ed_shopname_2;
    private Button btn_bianji_,btn_clean_;
    private Button btn_bianji_1,btn_clean_1;
    private Button btn_bianji_2,btn_clean_2;
    private MaxHeightRecyclerView recy_list_;
    private Button btn_save;
    private String School_id;
    private String store_id,store_id1,store_id2;
    private ShopItemAdapter adapter;
    private List<StoreBean> finalgoodsList = new ArrayList<>();
    private List<StoreBean> storeList = new ArrayList<>();
    private List<SchoolBean> schoolBeanList = new ArrayList<>();
    private NetUtils netUtils;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("品牌推荐",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brands;
    }

    @Override
    protected void findViews() {
        spinner = getView(R.id.spinner);
        ed_shopname_ = getView(R.id.ed_shopname_);
        ed_shopname_1 = getView(R.id.ed_shopname_1);
        ed_shopname_2 = getView(R.id.ed_shopname_2);

        btn_bianji_ = getView(R.id.btn_bianji_);
        btn_bianji_1 = getView(R.id.btn_bianji_1);
        btn_bianji_2 = getView(R.id.btn_bianji_2);

        btn_clean_ = getView(R.id.btn_clean_);
        btn_clean_1 = getView(R.id.btn_clean_1);
        btn_clean_2 = getView(R.id.btn_clean_2);

        recy_list_ = getView(R.id.recy_list_);
        btn_save = getView(R.id.btn_save);
        netUtils = new NetUtils(mActivity, netRequestCallBack);
    }
    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", School_id + "");
        map.put("status","0");
        map.put("type","0");
        netUtils.post("changestoresort/get_store_list", map);
    }
    @Override
    protected void formatViews() {
        btn_bianji_.setOnClickListener(this);
        btn_bianji_1.setOnClickListener(this);
        btn_bianji_2.setOnClickListener(this);

        btn_clean_.setOnClickListener(this);
        btn_clean_1.setOnClickListener(this);
        btn_clean_2.setOnClickListener(this);

        btn_save .setOnClickListener(this);
        ed_shopname_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                type = 0;
                storeList.clear();
                if(!TextUtils.isEmpty(editable.toString())){
                    for(StoreBean bean : finalgoodsList){
                        if(bean.getShop_name().contains(editable.toString())){
                            storeList.add(bean);
                        }
                    }
                }
                adapter.setNewData(storeList);
            }
        });

        ed_shopname_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                type = 1;
                storeList.clear();
                if(!TextUtils.isEmpty(editable.toString())){
                    for(StoreBean bean : finalgoodsList){
                        if(bean.getShop_name().contains(editable.toString())){
                            storeList.add(bean);
                        }
                    }
                }
                adapter.setNewData(storeList);
            }
        });

        ed_shopname_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                type = 2;
                storeList.clear();
                if(!TextUtils.isEmpty(editable.toString())){
                    for(StoreBean bean : finalgoodsList){
                        if(bean.getShop_name().contains(editable.toString())){
                            storeList.add(bean);
                        }
                    }
                }
                adapter.setNewData(storeList);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recy_list_.setLayoutManager(layoutManager);
        adapter = new ShopItemAdapter(R.layout.item_shop_,storeList);
        recy_list_.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(type == 0){
                    store_id = storeList.get(position).getId();
                    ed_shopname_.setText(storeList.get(position).getShop_name());
                }else if(type == 1){
                    store_id1 = storeList.get(position).getId();
                    ed_shopname_1.setText(storeList.get(position).getShop_name());
                }else {
                    store_id2 = storeList.get(position).getId();
                    ed_shopname_2.setText(storeList.get(position).getShop_name());
                }


            }
        });
    }

    @Override
    protected void formatData() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                School_id = schoolBeanList.get(i).getId();
                loadData();
                SPUtils.getInstance(mActivity).put("School_id", School_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        netUtils.post("changestoresort/get_school_list", null);


    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bianji_:
                //跳转至添加菜品页面
                Bundle bundle = new Bundle();
                bundle.putString("shopname",""+ed_shopname_.getText());
                bundle.putString("storeid",""+store_id);
                jumpTo(SelectDishesActivity.class,bundle);
                break;
            case R.id.btn_bianji_1:
                //跳转至添加菜品页面
                Bundle bundle1 = new Bundle();
                bundle1.putString("shopname",""+ed_shopname_1.getText());
                bundle1.putString("storeid",""+store_id1);
                jumpTo(SelectDishesActivity.class,bundle1);
                break;
            case R.id.btn_bianji_2:
                //跳转至添加菜品页面
                Bundle bundle2 = new Bundle();
                bundle2.putString("shopname",""+ed_shopname_2.getText());
                bundle2.putString("storeid",""+store_id2);
                jumpTo(SelectDishesActivity.class,bundle2);
                break;
            case R.id.btn_clean_:
                ed_shopname_.setText("");
                store_id = "";
                break;
            case R.id.btn_clean_1:
                ed_shopname_1.setText("");
                store_id1 = "";
                break;
            case R.id.btn_clean_2:
                ed_shopname_2.setText("");
                store_id2 = "";
                break;
            case R.id.btn_save:
                // todo 保存
                break;
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
                case "changestoresort/get_school_list":
                    schoolBeanList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t, SchoolBean.class);
                    List<String> strings = new ArrayList<>();
                    for (SchoolBean bean : schoolBeanList) {
                        strings.add(bean.getName());
                    }
                    ArrayAdapter madapter = new ArrayAdapter(mActivity, R.layout.item, R.id.tv_mytext, strings);
                    spinner.setAdapter(madapter);
                    if (!TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("School_id"))) {
                        for (int i = 0; i < schoolBeanList.size(); i++) {
                            if (SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())) {
                                spinner.setSelection(i);
                                School_id = schoolBeanList.get(i).getId();

                            }
                        }
                    } else {
                        if (schoolBeanList.size() > 0) {
                            School_id = schoolBeanList.get(0).getId();

                        }
                    }
                    break;
                case "changestoresort/get_store_list":
                    finalgoodsList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t, StoreBean.class);
                    ed_shopname_.setText("");
                    ed_shopname_1.setText("");
                    ed_shopname_2.setText("");
                    storeList.clear();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
