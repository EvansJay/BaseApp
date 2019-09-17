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

/**
 * 首页搜索关键词
 * @author Andlei
 * @date 2019/9/10.
 */
public class IndexSearchKeywordActivity extends BaseLayoutActivity {
    private Spinner spinner;
    private ClearEditText ed_search_keyword,ed_search_shopname;
    private RecyclerView recy_list_;
    private Button btn_save;
    private String School_id;
    private String store_id;
    private ShopItemAdapter adapter;
    private List<StoreBean> finalgoodsList = new ArrayList<>();
    private List<StoreBean> storeList = new ArrayList<>();
    private List<SchoolBean> schoolBeanList = new ArrayList<>();
    private NetUtils netUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索关键词",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_search;
    }

    @Override
    protected void findViews() {
        spinner = getView(R.id.spinner);
        recy_list_ = getView(R.id.recy_list_);
        btn_save = getView(R.id.btn_save);
        ed_search_keyword = getView(R.id.ed_search_keyword);
        ed_search_shopname = getView(R.id.ed_search_shopname);
        netUtils = new NetUtils(mActivity, netRequestCallBack);
    }

    @Override
    protected void formatViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recy_list_.setLayoutManager(layoutManager);
        adapter = new ShopItemAdapter(R.layout.item_shop_,storeList);
        recy_list_.setAdapter(adapter);
        btn_save.setOnClickListener(this);
        ed_search_shopname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                store_id = storeList.get(position).getId();
                ed_search_shopname.setText(storeList.get(position).getShop_name());

            }
        });
    }
    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", School_id + "");
        map.put("status","0");
        map.put("type","0");
        netUtils.post("changestoresort/get_store_list", map);
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
            case R.id.btn_save:
                // todo 搜索关键词 保存

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
                    ed_search_keyword.setText("");
                    ed_search_shopname.setText("");
                    store_id = "";
                    storeList.clear();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
