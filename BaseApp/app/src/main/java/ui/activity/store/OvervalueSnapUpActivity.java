package ui.activity.store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.andlei.baseapp.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import base.BaseBean;
import bean.SchoolBean;
import netWork.NetUtils;
import utils.GsonUtils;


/**
 * 超值抢购-
 * @author Andlei
 * @date 2019/9/9.
 */
public class OvervalueSnapUpActivity extends activity.BaseLayoutActivity {
    private Spinner spinner;
    private Button btn_add,btn_manager;
    private NetUtils netUtils;
    private String School_id;
    private List<SchoolBean> schoolBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("超值抢购",true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_overvalue;
    }

    @Override
    protected void findViews() {
        spinner = getView(R.id.spinner);
        btn_add = getView(R.id.btn_add);
        btn_manager = getView(R.id.btn_manager);
        netUtils = new NetUtils(mContext,netRequestCallBack);
    }

    @Override
    protected void formatViews() {
        btn_add.setOnClickListener(this);
        btn_manager.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                School_id = schoolBeanList.get(i).getId();
                com.andlei.utils.SPUtils.getInstance(mActivity).put("School_id", School_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        netUtils.post("changestoresort/get_school_list", null);
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                Bundle bundle = new Bundle();
                bundle.putString("school_id",School_id+"");
                jumpTo(AddingDishesActivity.class,bundle);
                break;
            case R.id.btn_manager:
                Bundle bundle1 = new Bundle();
                bundle1.putString("school_id",School_id+"");
                jumpTo(PosterListActivity.class,bundle1);
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
                    ArrayAdapter adapter = new ArrayAdapter(mActivity, R.layout.item, R.id.tv_mytext, strings);
                    spinner.setAdapter(adapter);
                    if (!TextUtils.isEmpty(com.andlei.utils.SPUtils.getInstance(mActivity).getString("School_id"))) {
                        for (int i = 0; i < schoolBeanList.size(); i++) {
                            if (com.andlei.utils.SPUtils.getInstance(mActivity).getString("School_id").equals(schoolBeanList.get(i).getId())) {
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
//                case "changestoresort/get_config_info":
//                    List<SortTypeBean> typeBeans = GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t,SortTypeBean.class);
//                    initData(typeBeans);
//                    break;
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
}
