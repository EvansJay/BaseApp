package ui.activity.store;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.SchoolBean;
import bean.SortTypeBean;
import bean.SortTypeNewBean;
import netWork.NetUtils;
import utils.GsonUtils;
import utils.SPUtils;

/**
 * 店铺排序
 *
 * @author Andlei
 * @date 2019/8/26.
 */
public class StoreSortActivity extends BaseLayoutActivity {
    private Spinner spinner;
    private TextView tv_waimai, tv_ziqu;
    private ImageView img_moren, img_auto;
    private Button btn_moren, btn_auto;
    private Button btn_saveData;
    private Boolean isCheckMoRen = false;
    private Boolean isCheckAuto = false;
    private Boolean isCheckMoRen_ziqu = false;
    private Boolean isCheckAuto_ziqu = false;
    private List<SchoolBean> schoolBeanList = new ArrayList<>();
    private List<SortTypeBean> sortTypeBeans = new ArrayList<>();
    private String School_id;
    private NetUtils netUtils;
    private int type = 1;

    //type 	true 	0默认 1外卖默认 2外卖早餐 3外卖正餐 4外卖下午茶 5外卖夜宵 6自取默认 7自取早餐 8自取正餐 9自取下午茶 10自取夜宵 11外卖智能 12自取智能
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("店铺排序", true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_sort;
    }

    @Override
    protected void findViews() {
        spinner = getView(R.id.spinner);
        tv_waimai = getView(R.id.tv_waimai_tap);
        tv_ziqu = getView(R.id.tv_ziqu_tap);
        img_moren = getView(R.id.img_sort);
        img_auto = getView(R.id.img_auto_sort);
        btn_moren = getView(R.id.btn_bianji);
        btn_auto = getView(R.id.btn_bianji_auto);
        btn_saveData = getView(R.id.btn_saveData);
        netUtils = new NetUtils(mActivity, netRequestCallBack);
    }

    @Override
    protected void formatViews() {
        tv_waimai.setOnClickListener(this);
        tv_ziqu.setOnClickListener(this);
        img_moren.setOnClickListener(this);
        img_auto.setOnClickListener(this);
        btn_moren.setOnClickListener(this);
        btn_auto.setOnClickListener(this);
        btn_saveData.setOnClickListener(this);
    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", School_id);
        netUtils.post("changestoresort/get_config_info", map);
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
        switch (view.getId()) {
            case R.id.tv_waimai_tap:
                type = 1;
                tv_waimai.setBackgroundResource(R.drawable.shape_green);
                tv_ziqu.setBackgroundResource(R.drawable.shape_grey);
                loadData();
                break;
            case R.id.tv_ziqu_tap:
                type = 6;
                tv_waimai.setBackgroundResource(R.drawable.shape_grey);
                tv_ziqu.setBackgroundResource(R.drawable.shape_green);
                loadData();
                break;
            case R.id.img_sort:
                if (type == 1) {
                    isCheckMoRen = !isCheckMoRen;
                    updateSort();
                } else {
                    isCheckMoRen_ziqu = !isCheckMoRen_ziqu;
                    updateSort_ziqu();
                }
                break;
            case R.id.img_auto_sort:
                if (type == 1) {
                    isCheckAuto = !isCheckAuto;
                    updateAutoSort();
                } else {
                    isCheckAuto_ziqu = !isCheckAuto_ziqu;
                    updateAutoSort_ziqu();
                }
                break;
            case R.id.btn_bianji:
                Bundle bundle = new Bundle();
                bundle.putString("title", "默认");
                bundle.putString("School_id", School_id);
                bundle.putInt("type", type);
                jumpTo(SortActivity.class, bundle);
                break;
            case R.id.btn_bianji_auto:
                Bundle bundle2 = new Bundle();
                bundle2.putString("School_id", School_id);
                bundle2.putInt("type", type);
                bundle2.putParcelableArrayList("sortTypeBeans", (ArrayList<? extends Parcelable>) sortTypeBeans);
                jumpTo(MindSortActivity.class, bundle2);
                break;
            case R.id.btn_saveData://保存修改
                Map<String, Object> map = new HashMap<>();
                map.put("school_id", School_id);
                List<SortTypeBean> typeBeans = new ArrayList<>();
                SortTypeBean typeBean = new SortTypeBean();
                SortTypeBean typeBean1 = new SortTypeBean();
                if (type == 1) {
                    typeBean.setType(1);
                    typeBean1.setType(11);
//                    if (isCheckMoRen) {
//                        typeBean.setEnable(1);
//                    } else {
//                        typeBean.setEnable(0);
//                    }

                    if (isCheckAuto) {
                        typeBean1.setEnable(1);
                        typeBean.setEnable(0);
                    } else {
                        typeBean1.setEnable(0);
                        typeBean.setEnable(1);
                    }
                    typeBeans.add(typeBean);
                    typeBeans.add(typeBean1);
                } else {
                    typeBean.setType(6);
                    typeBean1.setType(12);
//                    if (isCheckMoRen_ziqu) {
//                        typeBean.setEnable(1);
//                    } else {
//                        typeBean.setEnable(0);
//                    }

                    if (isCheckAuto_ziqu) {
                        typeBean1.setEnable(1);
                        typeBean.setEnable(0);
                    } else {
                        typeBean1.setEnable(0);
                        typeBean.setEnable(1);
                    }
                    typeBeans.add(typeBean);
                    typeBeans.add(typeBean1);
                }
                map.put("sort_list", new Gson().toJson(typeBeans));
                netUtils.post("changestoresort/update_sort_config", map);
                break;
        }
    }

    private void updateSort() {
        if (isCheckMoRen) {
            img_moren.setImageResource(R.mipmap.icon_button_on);
        } else {
            img_moren.setImageResource(R.mipmap.icon_button_off);
        }
    }

    private void updateAutoSort() {
        if (isCheckAuto) {
            img_auto.setImageResource(R.mipmap.icon_button_on);
        } else {
            img_auto.setImageResource(R.mipmap.icon_button_off);
        }
    }

    private void updateSort_ziqu() {
        if (isCheckMoRen_ziqu) {
            img_moren.setImageResource(R.mipmap.icon_button_on);
        } else {
            img_moren.setImageResource(R.mipmap.icon_button_off);
        }
    }

    private void updateAutoSort_ziqu() {
        if (isCheckAuto_ziqu) {
            img_auto.setImageResource(R.mipmap.icon_button_on);
        } else {
            img_auto.setImageResource(R.mipmap.icon_button_off);
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
                    ArrayAdapter adapter = new ArrayAdapter(mActivity, R.layout.item, R.id.tv_mytext, strings);
                    spinner.setAdapter(adapter);
                    if (!TextUtils.isEmpty(SPUtils.getInstance(mActivity).getString("School_id"))) {
                        for (int i = 0; i < schoolBeanList.size(); i++) {
                            if (SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())) {
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
                case "changestoresort/get_config_info":
                    List<SortTypeBean> typeBeans = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t,SortTypeBean.class);
                    initData(typeBeans);
                    break;
                case "changestoresort/update_sort_config":
                    if (t.ok == 1) {
                        toast("保存修改成功");
                        finish();
                    } else {
                        toast(t.getMsg());
                    }
                    break;
            }
        }
    };

    private void initData(List<SortTypeBean> typeBeans) {
        //0默认 1外卖默认 2外卖早餐 3外卖正餐 4外卖下午茶 5外卖夜宵 6自取默认 7自取早餐 8自取正餐 9自取下午茶 10自取夜宵 11外卖智能 12自取智能
        sortTypeBeans = typeBeans;
        if(type == 1){
            for (SortTypeBean typeBean : typeBeans){
                if(typeBean.getType() == 1){
                    if(typeBean.getEnable() == 0){
                        isCheckMoRen = false;
                    }else {
                        isCheckMoRen = true;
                    }
                    updateSort();
                }
                if(typeBean.getType() == 11){
                    if(typeBean.getEnable() == 0){
                        isCheckAuto = false;
                    }else {
                        isCheckAuto = true;
                    }
                    updateAutoSort();
                }
            }
        }else {
            for (SortTypeBean typeBean : typeBeans){
                if(typeBean.getType() == 6){
                    if(typeBean.getEnable() == 0){
                        isCheckMoRen_ziqu = false;
                    }else {
                        isCheckMoRen_ziqu = true;
                    }
                    updateSort_ziqu();
                }
                if(typeBean.getType() == 12){
                    if(typeBean.getEnable() == 0){
                        isCheckAuto_ziqu = false;
                    }else {
                        isCheckAuto_ziqu = true;
                    }
                    updateAutoSort_ziqu();
                }
            }
        }

    }
}
