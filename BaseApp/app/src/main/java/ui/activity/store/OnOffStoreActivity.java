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
import android.widget.Spinner;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import adapter.StoreOnOffListAdapter;
import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.SchoolBean;
import bean.StoreBean;
import netWork.NetUtils;
import utils.DensityUtil;
import utils.GsonUtils;
import utils.SPUtils;
import widget.ClearEditText;
import widget.HiDialog;
import widget.OnOffStoreTypePopupWindow;
import widget.ShopSelectPopupWindow;


/**
 * 开关店铺
 *
 * @author Andlei
 * @date 2019/6/24.
 */
public class OnOffStoreActivity extends BaseLayoutActivity {
    private Spinner spinner;
//    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView,recyclerView_isSelect;
    private StoreOnOffListAdapter storeOnOffListAdapter_select;
    private StoreOnOffListAdapter storeOnOffListAdapter;
    private List<StoreBean> list_store_select = new ArrayList<>();
    private List<StoreBean> finalgoodsList = new ArrayList<>();
    private List<StoreBean> goodsList = new ArrayList<>();
    private NetUtils netUtils;
    private List<SchoolBean> schoolBeanList = new ArrayList<>();
    private String School_id;
    private TextView tv_store_type,tv_numtext,tv_noselect;
    private Button btn_open;
    private Button btn_close;
    private boolean is_open;
    private boolean is_one;
    private CheckBox checkBox;
    private List<String> listid = new ArrayList<>();
    private List<StoreBean> listid_all = new ArrayList<>();
    private List<String> listid_one = new ArrayList<>();
    private ClearEditText clearEditText;
    private boolean isClickSelect;
    private int storetype = 0; //默认 全部
    private OnOffStoreTypePopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("开关店铺");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_onoff;
    }

    @Override
    protected void findViews() {
        netUtils = new NetUtils(mContext, netRequestCallBack);
        tv_store_type = getView(R.id.tv_store_type);
       // refreshLayout = getView(R.id.rcy_list_refrsh);
        recyclerView_isSelect = getView(R.id.rcy_list_select);
        recyclerView = getView(R.id.rcy_list);
        spinner = getView(R.id.spinner);
        clearEditText = getView(R.id.edittext_search);
        tv_numtext = getView(R.id.tv_storenum);
        tv_noselect = getView(R.id.tv_noselect);
        btn_close = getView(R.id.btn_close);
        btn_open = getView(R.id.btn_open);
        checkBox = getView(R.id.checkbox);
        netUtils.post("changestoresort/get_school_list", null);
    }
    private void loadData(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", id + "");
       // map.put("keyword", clearEditText.getText().toString());
        map.put("status",storetype+"");
        map.put("type","0");
        netUtils.post("changestoresort/get_store_list", map);
    }

    private void setOnoffData(List<String> listid, Integer is_open) {
        Map<String, Object> map = new HashMap<>();
        map.put("store_id", listid);
        map.put("is_open", is_open);    //1代表休息  0代表营业
        netUtils.post("changestoresort/update_business_status", map);
    }

    @Override
    protected void formatViews() {
        tv_store_type.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                School_id = schoolBeanList.get(i).getId();
                loadData(School_id);
                tv_numtext.setText("已选择0家店");
                list_store_select.clear();
                storeOnOffListAdapter_select.notifyDataSetChanged();
                updateText();
                SPUtils.getInstance(mActivity).put("School_id", School_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                    goodsList.addAll(finalgoodsList);
                    goodsList.removeAll(list_store_select);
//                    for (StoreBean bean: list_store_select){
//                        Iterator<StoreBean> it_b = goodsList.iterator();
//                        while (it_b.hasNext()){
//                            StoreBean bean1 = it_b.next();
//                            if(bean1.getId().equals(bean.getId())){
//                                goodsList.remove(bean1);
//                            }
//                        }
//                    }
                    storeOnOffListAdapter.setNewData(goodsList);
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
                    if(!TextUtils.isEmpty(clearEditText.getText())){
                        goodsList.clear();
                        for(StoreBean bean : finalgoodsList){
                            if(bean.getShop_name().contains(clearEditText.getText())){
                                goodsList.add(bean);
                            }
                        }
                        goodsList.removeAll(list_store_select);
                        storeOnOffListAdapter.setNewData(goodsList);
                    }

                }
                return false;
            }
        });

        recyclerView_isSelect.setLayoutManager(new LinearLayoutManager(mActivity));
        storeOnOffListAdapter_select = new StoreOnOffListAdapter(R.layout.item_onoff_store, list_store_select);
        recyclerView_isSelect.setAdapter(storeOnOffListAdapter_select);
        storeOnOffListAdapter_select.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.img_select:
                        list_store_select.get(position).isSelect = false;
                        AddBeanNoHas(list_store_select,goodsList,position);
                        updateText();

                        tv_numtext.setText("已选择" + list_store_select.size() + "家店");
                        storeOnOffListAdapter_select.notifyDataSetChanged();
                        storeOnOffListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.switch_tab:
                        isClickSelect = true;
                        is_one = true;
                        listid_one.clear();
                        listid_one.add(list_store_select.get(position).getId()+"");
                        if (list_store_select.get(position).getIs_open() == 0) {
                            is_open = true;
                            setOnoffData(listid_one, 1);
                        } else {
                            is_open = false;
                            setOnoffData(listid_one, 0);
                        }

                        break;
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        storeOnOffListAdapter = new StoreOnOffListAdapter(R.layout.item_onoff_store, goodsList);
        recyclerView.setAdapter(storeOnOffListAdapter);
        storeOnOffListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //JumpToUtils.JumpToNewApp(goodsList.get(position).getAccount(),goodsList.get(position).getPassword());
            }
        });
        storeOnOffListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_select:
                        goodsList.get(position).isSelect = true;
                        AddBeanNoHas(goodsList,list_store_select,position);
                        updateText();

                        tv_numtext.setText("已选择" + list_store_select.size() + "家店");
                        storeOnOffListAdapter_select.notifyDataSetChanged();
                        storeOnOffListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.switch_tab:
                        isClickSelect = false;
                        is_one = true;
                        listid_one.clear();
                        listid_one.add(goodsList.get(position).getId()+"");
                        if (goodsList.get(position).getIs_open() == 0) {
                            is_open = true;
                            setOnoffData(listid_one, 1);
                        } else {
                            is_open = false;
                            setOnoffData(listid_one, 0);
                        }

                        break;
                }
            }
        });
        btn_open.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    for (int i = 0;i<goodsList.size();i++) {
                        goodsList.get(i).isSelect = true;
                    }
                    list_store_select.addAll(goodsList);
//                    list_store_select = goodsList;
                    goodsList.clear();
                } else {
                    list_store_select.clear();
                    loadData(School_id);
                }
                updateText();
                storeOnOffListAdapter_select.notifyDataSetChanged();
                storeOnOffListAdapter.notifyDataSetChanged();
            }
        });
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData(School_id);
//            }
//        });
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }
    private void AddBeanNoHas(List<StoreBean> list,List<StoreBean> list_add,int position){
        boolean ishas = false;
        for (StoreBean bean : list_add){
            if(bean.getId().equals(list.get(position).getId())){
                ishas = true;
            }
        }
        if(!ishas){
            list_add.add(list.get(position));
            list.remove(position);
        }else {
            toast("店铺已经选中");
        }
    }
    private void updateText(){
        if(list_store_select.size()>0){
            tv_noselect.setVisibility(View.VISIBLE);
            tv_numtext.setText("已选择" + list_store_select.size() + "家店");
        }else {
            tv_noselect.setVisibility(View.GONE);
            tv_numtext.setText("已选择0家店");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_store_type:
                    pop = new OnOffStoreTypePopupWindow(mActivity, storetype,new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //type 	true 	类型0全部 1商家 2后台
                            switch (view.getId()){
                                case R.id.tv_open:
                                    storetype = 2;
                                    TextView textView = view.findViewById(R.id.tv_open);
                                    tv_store_type.setText(textView.getText());
                                    loadData(School_id);
                                    break;
                                case R.id.tv_close:
                                    storetype = 1;
                                    TextView textView1 = view.findViewById(R.id.tv_close);
                                    tv_store_type.setText(textView1.getText());
                                    loadData(School_id);
                                    break;
                                case R.id.tv_third:
                                    storetype = 0;
                                    TextView textView2 = view.findViewById(R.id.tv_third);
                                    tv_store_type.setText(textView2.getText());
                                    loadData(School_id);
                                    break;
                            }
                            pop.dismiss();
                        }
                    });
                pop.showAtLocation(tv_store_type, Gravity.TOP | Gravity.CENTER_HORIZONTAL,0, DensityUtil.dip2px(OnOffStoreActivity.this,175));
                break;
            case R.id.btn_close:
                if (list_store_select.size() <= 0) {
                    toast("请选择店铺");
                    return;
                }
                is_one = false;
                is_open = false;
                listid.clear();
                for(StoreBean bean:list_store_select){
                    listid.add(bean.getId());
                }
                new HiDialog.Builder(OnOffStoreActivity.this)
                        .setContent("确定关闭所选的"+listid.size()+"家店铺吗？")
                        .setLeftBtnText("取消")
                        .setRightBtnText("确定")
                        .setRightCallBack(new HiDialog.RightClickCallBack() {
                            @Override
                            public void dialogRightBtnClick() {
                                setOnoffData(listid, 0);
                            }
                        }).build();
                break;
            case R.id.btn_open:
                if (list_store_select.size() <= 0) {
                    toast("请选择店铺");
                    return;
                }
                is_one = false;
                is_open = true;
                listid.clear();
                for(StoreBean bean:list_store_select){
                    listid.add(bean.getId());
                }
                new HiDialog.Builder(OnOffStoreActivity.this)
                        .setContent("确定打开所选的"+listid.size()+"家店铺吗？")
                        .setLeftBtnText("取消")
                        .setRightBtnText("确定")
                        .setRightCallBack(new HiDialog.RightClickCallBack() {
                            @Override
                            public void dialogRightBtnClick() {
                                setOnoffData(listid, 1);
                            }
                        }).build();
                break;
            case R.id.tv_storenum:

                break;
        }
    }

    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean baseBean, Map tag) {
            //refreshLayout.setRefreshing(false);
            if (baseBean.ok != 1) {
                toast(baseBean.msg);
                return;
            }
            switch (action) {
                case "changestoresort/get_school_list":
                    schoolBeanList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean, SchoolBean.class);
                    List<String> strings = new ArrayList<>();
                    for (SchoolBean bean : schoolBeanList) {
                        strings.add(bean.getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(mActivity, R.layout.item, R.id.tv_mytext, strings);
                    spinner.setAdapter(adapter);
                    if (!TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("School_id"))) {
                        for (int i = 0; i < schoolBeanList.size(); i++) {
                            if (SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())) {
                                spinner.setSelection(i);
                                School_id = schoolBeanList.get(i).getId();
                                loadData(School_id);
                            }
                        }
                    } else {
                        if (schoolBeanList.size() > 0) {
                            School_id = schoolBeanList.get(0).getId();
                            loadData(School_id);
                        }
                    }
                    break;
                case "changestoresort/get_store_list":
                    finalgoodsList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean, StoreBean.class);
                    goodsList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(baseBean, StoreBean.class);
//                    for (int i = 0 ; i<goodsList.size();i++){
//                        if(listid.contains(goodsList.get(i).getId())){
//                            //bean.isSelect=true;
//                            goodsList.remove(i);
//                        }
//
//                    }
                    storeOnOffListAdapter.setNewData(goodsList);

                    list_store_select.clear();
                    updateText();
                    storeOnOffListAdapter_select.notifyDataSetChanged();
                    break;
                case "changestoresort/update_business_status":
                    if (baseBean.ok == 1) {
                        if (is_one) {
                            if(isClickSelect){
                                for (StoreBean bean : list_store_select) {
                                    if (bean.getId().equals(listid_one.get(0))) {
                                        if (is_open) {
                                            bean.setIs_open(1);
                                        } else {
                                            bean.setIs_open(0);
                                        }
                                    }
                                }
                            }else {
                                for (StoreBean bean : goodsList) {
                                    if (bean.getId().equals(listid_one.get(0))) {
                                        if (is_open) {
                                            bean.setIs_open(1);
                                        } else {
                                            bean.setIs_open(0);
                                        }
                                    }
                                }
                            }
                       } else {
                            for (StoreBean bean : goodsList) {
                                for (int i = 0; i < listid.size(); i++) {
                                    if (bean.getId() .equals(listid.get(i))) {
                                        if (is_open) {
                                            bean.setIs_open(1);
                                        } else {
                                            bean.setIs_open(0);
                                        }
                                    }
                                }
                            }
                            for (StoreBean bean : list_store_select) {
                                for (int i = 0; i < listid.size(); i++) {
                                    if (bean.getId() == listid.get(i)) {
                                        if (is_open) {
                                            bean.setIs_open(1);
                                        } else {
                                            bean.setIs_open(0);
                                        }
                                    }
                                }
                            }
                            loadData(School_id);
                        }
                        storeOnOffListAdapter_select.notifyDataSetChanged();
                        storeOnOffListAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
            //refreshLayout.setRefreshing(false);
            toast(e+"");
        }
    };
}
