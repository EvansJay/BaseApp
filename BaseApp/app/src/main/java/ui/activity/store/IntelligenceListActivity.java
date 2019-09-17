package ui.activity.store;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import java.util.List;
import java.util.Map;

import adapter.IntelligenceListAdapter;
import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.SchoolBean;
import bean.StoreBean;
import netWork.NetUtils;
import utils.GsonUtils;
import utils.SPUtils;
import widget.ClearEditText;

/**
 * 资质列表
 * @author Andlei
 * @date 2019/7/2.
 */
public class IntelligenceListActivity extends BaseLayoutActivity {
    private Spinner spinner_school;
    private ClearEditText editText;
    private List<StoreBean> lists = new ArrayList<>();
    private List<String> listid = new ArrayList<>();
    private List<StoreBean> listid_all = new ArrayList<>();
    private List<SchoolBean> schoolBeanList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NetUtils netUtils;
    private CheckBox checkBox;
    private TextView tv_select;
    private Button btn_allset;
    private Spinner spinner_state;
    private TextView tv_time;
    private String School_id;
    private IntelligenceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("资质列表",true);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_intelligence_list;
    }

    @Override
    protected void findViews() {
        netUtils = new NetUtils(mContext, netRequestCallBack);
        spinner_school = getView(R.id.spinner);
        editText = getView(R.id.edittext_search);
        checkBox = getView(R.id.checkbox);
        tv_select = getView(R.id.tv_storenum);
        btn_allset = getView(R.id.btn_setall);
        spinner_state = getView(R.id.edit_peisongfei_all);
        tv_time = getView(R.id.edit_qisongfei_all);
        recyclerView = getView(R.id.recy_list);
    }

    @Override
    protected void formatViews() {
        //学校选择
        spinner_school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable.toString())){
                    //为空则重新加载数据
                    loadData(School_id);
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    loadData(School_id);
                }
                return false;
            }
        });
        //状态选择
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    for (StoreBean bean : lists) {
                        bean.isSelect = true;
                        listid.add(bean.getId());
                        listid_all.add(bean);
                        tv_select.setText("已选择" + lists.size() + "家店");
                    }
                } else {
                    for (StoreBean bean : lists) {
                        bean.isSelect = false;
                    }
                    listid.clear();
                    listid_all.clear();
                    tv_select.setText("已选择0家店");
                }
                adapter.notifyDataSetChanged();
            }
        });
        btn_allset.setOnClickListener(this);
        tv_time.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new IntelligenceListAdapter(R.layout.item_onoff_store,lists);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){

                }
            }
        });
    }
    private void loadData(String school_id){
        //请求加载
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", school_id + "");
        map.put("keyword", editText.getText().toString());
        netUtils.post("changestoresort/get_store_list", map);
    }
    @Override
    protected void formatData() {
        netUtils.post("changestoresort/get_school_list", null);
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //选择时间
            case R.id.edit_qisongfei_all:

                break;
            //批量设置
            case R.id.btn_setall:

                break;
        }
    }
    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean baseBean, Map tag) {
            refreshLayout.setRefreshing(false);
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
                    spinner_school.setAdapter(adapter);
                    if (!TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("School_id"))) {
                        for (int i = 0; i < schoolBeanList.size(); i++) {
                            if (SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())) {
                                spinner_school.setSelection(i);
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
            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
            refreshLayout.setRefreshing(false);
            toast(e+"");
        }
    };
}
