package ui.activity.rider;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import base.BaseBean;
import bean.SchoolBean;
import bean.SchoolDistributionBean;
import netWork.NetUtils;
import ui.activity.store.OnOffStoreActivity;
import utils.GsonUtils;

import widget.HiDialog;
import widget.SelectBuildingPopupWindow;

/**
 * @author Andlei
 * @date 2019/8/17.
 */
public class DistributionSettingActivity extends activity.BaseLayoutActivity {
    private EditText ced_inshcool_base,
            ced_inshcool_reward,
            ced_inshcool_allowance,
            ced_inshcool_aging,
            ced_outshcool_money,
            ced_outshcool_reward,
            ced_outshcool_allowance,
            ced_outshcool_aging;
    private Button btn_save;
    private NetUtils netUtils;
    private Spinner spinner;
    private String School_id;
    private TextView tv_select;
    private List<SchoolBean> schoolBeanList = new ArrayList<>();
    private List<SchoolDistributionBean.BuildingListBean> BuildingList = new ArrayList<>();
    private SelectBuildingPopupWindow pop;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("配送设置", true);
        setOnClickListener(R.id.base_back);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distrbution_setting;
    }

    @Override
    protected void findViews() {
        netUtils = new NetUtils(mActivity, netRequestCallBack);
        ced_inshcool_base = getView(R.id.ced_inshcool_base);
        ced_inshcool_reward = getView(R.id.ced_inshcool_reward);
        ced_inshcool_allowance = getView(R.id.ced_inshcool_allowance);
        ced_inshcool_aging = getView(R.id.ced_inshcool_aging);
        ced_outshcool_money = getView(R.id.ced_outshcool_money);
        ced_outshcool_reward = getView(R.id.ced_outshcool_reward);
        ced_outshcool_allowance = getView(R.id.ced_outshcool_allowance);
        ced_outshcool_aging = getView(R.id.ced_outshcool_aging);
        btn_save = getView(R.id.btn_save);
        spinner = getView(R.id.spinner);
        tv_select = getView(R.id.tv_select_loud);

        netUtils.post("changestoresort/get_school_list", null);
    }

    @Override
    protected void formatViews() {
        tv_select.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        ced_inshcool_base.setFilters(new InputFilter[]{lengthFilter});
        ced_inshcool_reward.setFilters(new InputFilter[]{lengthFilter});
        ced_inshcool_allowance.setFilters(new InputFilter[]{lengthFilter});
        ced_inshcool_aging.setFilters(new InputFilter[]{lengthFilter});
        ced_outshcool_money.setFilters(new InputFilter[]{lengthFilter});
        ced_outshcool_reward.setFilters(new InputFilter[]{lengthFilter});
        ced_outshcool_allowance.setFilters(new InputFilter[]{lengthFilter});
        ced_outshcool_aging.setFilters(new InputFilter[]{lengthFilter});
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                School_id = schoolBeanList.get(i).getId();
                loadData();
                com.andlei.utils.SPUtils.getInstance(mActivity).put("School_id", School_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", School_id);
        netUtils.post("changestoresort/get_address_list", map);
    }

    private void updateData() {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", School_id);
        map.put("money", ced_inshcool_base.getText().toString().trim());
        map.put("in_reward", ced_inshcool_reward.getText().toString().trim());
        map.put("in_sub", ced_inshcool_allowance.getText().toString().trim());
        map.put("in_deadline", ced_inshcool_aging.getText().toString().trim());
        map.put("out_money", ced_outshcool_money.getText().toString().trim());
        map.put("out_reward", ced_outshcool_reward.getText().toString().trim());
        map.put("out_sub", ced_outshcool_allowance.getText().toString().trim());
        map.put("out_deadline", ced_outshcool_aging.getText().toString().trim());
        map.put("buildingList", GsonUtils.beanToJSONString(BuildingList));
        netUtils.post("changestoresort/set_distribution", map);
    }

    @Override
    protected void formatData() {
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_back:
                if (isUpdate) {
                    new HiDialog.Builder(DistributionSettingActivity.this)
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
                                    updateData();
                                }
                            }).build();
                } else {
                    finish();
                }
                break;
            case R.id.tv_select_loud:
                if (pop == null) {
                    pop = new SelectBuildingPopupWindow(mContext, BuildingList, new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.img_jia:
                                    BuildingList.get(position).b_money = "" + com.andlei.utils.TxtUtils.setDoubleScale((Double.parseDouble(BuildingList.get(position).b_money) + 0.1), 1);
                                    break;
                                case R.id.img_jian:
                                    if (Double.parseDouble(BuildingList.get(position).b_money) <= 0.1) {
                                        toast("配送金额不能小于等于0元");
                                        return;
                                    }
                                    BuildingList.get(position).b_money = "" + com.andlei.utils.TxtUtils.setDoubleScale((Double.parseDouble(BuildingList.get(position).b_money) - 0.1), 1);
                                    break;
                            }
                            isUpdate = true;
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                pop.showAtLocation(tv_select, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_save:
                if(TextUtils.isEmpty(ced_inshcool_reward.getText().toString())){
                    toast("请输入校内骑手奖励");
                    return;
                }
                if(TextUtils.isEmpty(ced_inshcool_allowance.getText().toString())){
                    toast("请输入校内骑手恶劣天气补贴");
                    return;
                }
                if(TextUtils.isEmpty(ced_inshcool_aging.getText().toString())){
                    toast("请输入校内骑手时效");
                    return;
                }
                if(TextUtils.isEmpty(ced_outshcool_money.getText().toString())){
                    toast("请输入校外骑手基础配送费");
                    return;
                }
                if(TextUtils.isEmpty(ced_outshcool_reward.getText().toString())){
                    toast("请输入校外骑手奖励");
                    return;
                }
                if(TextUtils.isEmpty(ced_outshcool_allowance.getText().toString())){
                    toast("请输入校外骑手恶劣天气补贴");
                    return;
                }
                if(TextUtils.isEmpty(ced_outshcool_aging.getText().toString())){
                    toast("请输入校外骑手时效");
                    return;
                }

                updateData();
                break;
        }

    }

    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean t, Map tag) {
            switch (action) {
                case "changestoresort/get_school_list":
                    schoolBeanList = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t, SchoolBean.class);
                    List<String> strings = new ArrayList<>();
                    for (SchoolBean bean : schoolBeanList) {
                        strings.add(bean.getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(mActivity, R.layout.item, R.id.tv_mytext, strings);
                    spinner.setAdapter(adapter);
                    if (!TextUtils.isEmpty(com.andlei.utils.SPUtils.getInstance(mActivity).getString("School_id"))) {
                        for (int i = 0; i < schoolBeanList.size(); i++) {
                            if (com.andlei.utils.SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())) {
                                spinner.setSelection(i);
                                School_id = schoolBeanList.get(i).getId();
                                loadData();
                            }
                        }
                    } else {
                        if (schoolBeanList.size() > 0) {
                            School_id = schoolBeanList.get(0).getId();
                            loadData();
                        }
                    }
                    break;
                case "changestoresort/get_address_list":
                    SchoolDistributionBean bean = (SchoolDistributionBean) GsonUtils.getInstance(mActivity).parseComplexJArrayByCommon(t, SchoolDistributionBean.class);
                    initData(bean);
                    break;
                case "changestoresort/set_distribution":
                    if (t.ok == 1) {
                        toast("保存信息成功");
                        finish();
                    } else {
                        toast(t.getMsg());
                    }
                    break;
            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
            toast("" + e);
        }
    };

    private void initData(final SchoolDistributionBean bean) {
        //校内骑手时效设置
        ced_inshcool_base.setText(bean.getMoney());
        ced_inshcool_reward.setText(bean.getIn_reward());
        ced_inshcool_allowance.setText(bean.getIn_sub());
        ced_inshcool_aging.setText(bean.getIn_deadline()+"");
        //校外骑手时效设置
        ced_outshcool_money.setText(bean.getOut_money());
        ced_outshcool_reward.setText(bean.getOut_reward());
        ced_outshcool_allowance.setText(bean.getOut_sub());
        ced_outshcool_aging.setText(bean.getOut_deadline()+"");
        //楼栋设置
        BuildingList.clear();
        BuildingList.addAll(bean.getBuildingList());
        if (pop != null) {
            pop.UpdateData(BuildingList);
        }

        //判断是否修改页面内容
        ced_inshcool_allowance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getIn_sub())){
                    isUpdate = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ced_inshcool_reward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getIn_reward())){
                    isUpdate = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ced_inshcool_aging.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getIn_deadline()+"")){
                    isUpdate = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ced_outshcool_aging.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getOut_deadline()+"")){
                    isUpdate = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ced_outshcool_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getOut_money())){
                    isUpdate = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ced_outshcool_reward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getOut_reward())){
                    isUpdate = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ced_outshcool_allowance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(bean.getOut_sub())){
                    isUpdate = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onBackPressed() {

        if (isUpdate) {
            new HiDialog.Builder(DistributionSettingActivity.this)
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
                            updateData();
                        }
                    }).build();
        } else {
            finish();
        }
       // super.onBackPressed();
    }

    private InputFilter lengthFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            //Log.e("", "source=" + source + ",start=" + start + ",end=" + end + ",dest=" + dest.toString() + ",dstart=" + dstart + ",dend=" + dend);
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
//                if (dotValue.length() == 2) {//输入框小数的位数是2的情况，整个输入框都不允许输入
//                    return "";
//                }
                if (dotValue.length() == 1 && dest.length() - dstart < 2){ //输入框小数的位数是2的情况时小数位不可以输入，整数位可以正常输入
                    toast("只能输入保留一位小数");
                    return "";
                }
            }
            return null;
        }
    };
}
