package ui.activity.store;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import adapter.TrimStoreAdapter;
import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.SchoolBean;
import bean.StoreBean;
import netWork.NetUtils;
import utils.GsonUtils;
import utils.SPUtils;
import widget.ClearEditText;
import widget.ShopTrimPopupWindow;

/**
 * 调整费用店铺
 * @author Andlei
 * @date 2019/6/24.
 */
public class TrimStoreActivity extends BaseLayoutActivity {
    private Spinner spinner;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TrimStoreAdapter storeAdapter;
    private List<StoreBean> goodsList = new ArrayList<>();
    private List<StoreBean> finalgoodsList = new ArrayList<>();
    private NetUtils netUtils;
    List<SchoolBean> schoolBeanList = new ArrayList<>();
    private String School_id;
    private Button tv_numstore;
    private Button btn_set;
    private EditText edit_peisong,edit_qisong;
    private List<String> listid = new ArrayList<>();
    private List<StoreBean> listid_all =  new ArrayList<>();
    private ShopTrimPopupWindow popup;
    private CheckBox checkBox;
    private ClearEditText clearEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("调整费用");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trimstore;
    }

    @Override
    protected void findViews() {
        netUtils = new NetUtils(mContext,netRequestCallBack);
        refreshLayout = getView(R.id.rcy_list_refrsh);
        recyclerView = getView(R.id.rcy_list);
        spinner = getView(R.id.spinner);
        tv_numstore = getView(R.id.tv_storenum);
        clearEditText = getView(R.id.edittext_search);
        btn_set = getView(R.id.btn_setall);
        edit_peisong = getView(R.id.edit_peisongfei_all);
        edit_qisong = getView(R.id.edit_qisongfei_all);
        checkBox = getView(R.id.checkbox);
        netUtils.post("changestoresort/get_school_list",null);
    }

    @Override
    protected void formatViews() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                School_id  = schoolBeanList.get(i).getId();
                loadData(School_id);
                tv_numstore.setText("已选择0家店");
                listid.clear();
                listid_all.clear();
                SPUtils.getInstance(mActivity).put("School_id",School_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tv_numstore.setOnClickListener(this);
        clearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable.toString())){
                    //loadData(School_id);
                    goodsList.clear();
                    goodsList.addAll(finalgoodsList);
                    storeAdapter.setNewData(goodsList);
                }
            }
        });
        clearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                   // loadData(School_id);
                    goodsList.clear();
                    for(StoreBean bean : finalgoodsList){
                        if(bean.getShop_name().contains(clearEditText.getText().toString().trim())){
                            goodsList.add(bean);
                        }
                    }
                    storeAdapter.setNewData(goodsList);
                }
                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        storeAdapter = new TrimStoreAdapter(R.layout.item_trim_store,goodsList);
        recyclerView.setAdapter(storeAdapter);
        storeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               // JumpToUtils.JumpToNewApp(goodsList.get(position).getAccount(),goodsList.get(position).getPassword());
            }
        });
        storeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.img_select:
                        goodsList.get(position).isSelect = !goodsList.get(position).isSelect;

                        for (StoreBean bean : goodsList){
                            if (bean.isSelect) {
                                if (!listid.contains(bean.getId())) {
                                    listid.add(bean.getId());
                                    listid_all.add(bean);
                                }
                            } else {
                                if (listid.contains(bean.getId())) {
                                    listid.remove(bean.getId());
                                    listid_all.remove(bean);
                                }
                            }
                        }
                        tv_numstore.setText("已选择"+listid.size()+"家店");
                        storeAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_sure:
                        List<String> strings = new ArrayList<>();
                        strings.add(goodsList.get(position).getId());
                        setPriceData(strings,goodsList.get(position).getSend_price(),goodsList.get(position).getStart_send_price());
                        break;
                }
            }
        });
        btn_set.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for (StoreBean bean : goodsList){
                        bean.isSelect = true;
                        listid.add(bean.getId());
                        listid_all.add(bean);
                    }
                    tv_numstore.setText("已选择"+goodsList.size()+"家店");
                }else {
                    for (StoreBean bean : goodsList){
                        bean.isSelect = false;
                    }
                    listid.clear();
                    listid_all.clear();
                    tv_numstore.setText("已选择0家店");
                }
                storeAdapter.notifyDataSetChanged();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(School_id);
            }
        });
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }
    private void loadData(String id){
        Map<String,Object> map = new HashMap<>();
        map.put("school_id",id+"");
        map.put("status","0");
        map.put("type","0");
       // map.put("keyword", clearEditText.getText().toString());
        netUtils.post("changestoresort/get_store_list",map);
    }
    private void setPriceData(List<String> listid,String send_price,String start_send_price){
        Map<String,Object> map = new HashMap<>();
        map.put("store_id",listid);
        map.put("school_id",School_id+"");
        map.put("send_price",send_price);
        map.put("start_send_price",start_send_price);
        netUtils.post("changestoresort/update_business_price",map);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_setall:
                //批量设置配送费
                if(listid.size()<=0){
                    toast("请选择店铺");
                    return;
                }
                setPriceData(listid,edit_peisong.getText().toString(),edit_qisong.getText().toString());
                break;
            case R.id.tv_storenum:
                for (StoreBean bean : goodsList) {
                    if (bean.isSelect && !listid.contains(bean.getId())) {
                        listid_all.add(bean);
                    }
                }
                Iterator<StoreBean> it = listid_all.iterator();
                while(it.hasNext()){
                    StoreBean x = it.next();
                    if(!x.isSelect){
                        it.remove();
                    }
                }
                popup = new ShopTrimPopupWindow(mActivity, new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.img_select:
                                listid_all.get(position).isSelect = !listid_all.get(position).isSelect;
                                Iterator<StoreBean> it = listid_all.iterator();
                                while(it.hasNext()){
                                    StoreBean bean = it.next();
                                    if(bean.isSelect){
                                        if (!listid.contains(bean.getId())) {
                                            listid.add(bean.getId());
                                        }
                                    }else {
                                        if (listid.contains(bean.getId())) {
                                            listid.remove(bean.getId());
                                        }
                                        it.remove();
                                    }
                                }
                                for (StoreBean bean : goodsList) {
                                    if (listid.contains(bean.getId())) {
                                        bean.isSelect = true;
                                    } else {
                                        bean.isSelect = false;
                                    }
                                }
                                tv_numstore.setText("已选择" + listid.size() + "家店");
                                adapter.notifyDataSetChanged();
                                storeAdapter.notifyDataSetChanged();
                                break;
                            case R.id.btn_sure:
                                List<String> listid_one = new ArrayList<>();
                                listid_one.add(listid_all.get(position).getId());
                                if (goodsList.get(position).getDisable() == 1) {
                                    setPriceData(listid_one, listid_all.get(position).getSend_price(),listid_all.get(position).getStart_send_price());
                                } else {
                                    setPriceData(listid_one, listid_all.get(position).getSend_price(),listid_all.get(position).getStart_send_price());
                                }
                                break;
                        }
                    }
                }, listid_all);
                popup.showAtLocation(tv_numstore, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                //popup.showAtLocation(tv_numstore, Gravity.BOTTOM, 0, 0);
                break;
        }
    }
    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean baseBean, Map tag) {
            refreshLayout.setRefreshing(false);
            switch (action) {
                case "changestoresort/get_school_list":
                    schoolBeanList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean,SchoolBean.class);
                    List<String> strings = new ArrayList<>();
                    for(SchoolBean bean: schoolBeanList){
                        strings.add(bean.getName());
                    }
                    ArrayAdapter adapter =  new ArrayAdapter(mActivity,R.layout.item,R.id.tv_mytext,strings);
                    spinner.setAdapter(adapter);
                    if(!TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("School_id"))){
                        for(int i=0; i<schoolBeanList.size();i++){
                            if(SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())){
                                spinner.setSelection(i);
                                School_id = schoolBeanList.get(i).getId();
                                loadData(School_id);
                            }
                        }
                    }else {
                        if(schoolBeanList.size()>0){
                            School_id = schoolBeanList.get(0).getId();
                            loadData(School_id);
                        }
                    }
                    break;
                case "changestoresort/get_store_list":
                    finalgoodsList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean,StoreBean.class);
                    goodsList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean,StoreBean.class);
                    for (StoreBean bean:goodsList){
                        if(listid.contains(bean.getId())){
                            bean.isSelect=true;
                        }
                    }
                    storeAdapter.setNewData(goodsList);
                    break;
                case "changestoresort/update_business_price":
                    if(baseBean.ok == 1){
                        for(StoreBean bean :goodsList){
                            for (int i = 0; i<listid.size();i++){
                                if(bean.getId().equals(listid.get(i))){
                                    if(!TextUtils.isEmpty(edit_peisong.getText().toString())){
                                        bean.setSend_price(edit_peisong.getText().toString());
                                    }
                                    if(!TextUtils.isEmpty(edit_qisong.getText().toString())){
                                        bean.setStart_send_price(edit_qisong.getText().toString());
                                    }
                                }
                            }
                        }
                        storeAdapter.notifyDataSetChanged();
                        toast("修改成功");
                    }
                    break;
            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
            refreshLayout.setRefreshing(false);
            toast(e+"");
        }
    };
}
