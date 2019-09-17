package ui.activity.store;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseBean;
import base.activity.BaseLayoutActivity;
import bean.SortTypeBean;
import netWork.NetUtils;
import utils.GsonUtils;
import utils.Logger;

/**
 * 智能排序
 *
 * @author Andlei
 * @date 2019/8/26.
 */
public class MindSortActivity extends BaseLayoutActivity {
    private Button btn_bianji_auto, btn_bianji_auto_zheng, btn_bianji_auto_xiawu,btn_bianji_auto_yexiao_dinner, btn_bianji_auto_yexiao;
    private String School_id;
    private int p_type;
    private int type;
    private ArrayList<SortTypeBean> sortTypeBeans = new ArrayList<>();
    private TextView tv_start_time1, tv_end_time1, tv_start_time2, tv_end_time2, tv_start_time3, tv_end_time3,tv_start_time_dinner,tv_end_time_dinner,tv_start_time4, tv_end_time4;
    private Button btn_savetime;
    private TimePickerView pvCustomLunar;
    private NetUtils netUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能排序", true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mind_sort;
    }

    @Override
    protected void findViews() {
        btn_bianji_auto = getView(R.id.btn_bianji_auto);
        btn_bianji_auto_zheng = getView(R.id.btn_bianji_auto_zheng);
        btn_bianji_auto_xiawu = getView(R.id.btn_bianji_auto_xiawu);
        btn_bianji_auto_yexiao_dinner = getView(R.id.btn_bianji_auto_yexiao_dinner);
        btn_bianji_auto_yexiao = getView(R.id.btn_bianji_auto_yexiao);

        tv_start_time1 = getView(R.id.tv_start_time1);
        tv_start_time2 = getView(R.id.tv_start_time2);
        tv_start_time3 = getView(R.id.tv_start_time3);
        tv_start_time_dinner = getView(R.id.tv_start_time_dinner);
        tv_start_time4 = getView(R.id.tv_start_time4);

        tv_end_time1 = getView(R.id.tv_end_time1);
        tv_end_time2 = getView(R.id.tv_end_time2);
        tv_end_time3 = getView(R.id.tv_end_time3);
        tv_end_time_dinner = getView(R.id.tv_end_time_dinner);
        tv_end_time4 = getView(R.id.tv_end_time4);

        btn_savetime = getView(R.id.btn_savetime);
        netUtils = new NetUtils(mActivity,netRequestCallBack);
    }

    @Override
    protected void formatViews() {
        btn_bianji_auto.setOnClickListener(this);
        btn_bianji_auto_zheng.setOnClickListener(this);
        btn_bianji_auto_xiawu.setOnClickListener(this);
        btn_bianji_auto_yexiao.setOnClickListener(this);
        btn_bianji_auto_yexiao_dinner.setOnClickListener(this);

        tv_start_time1.setOnClickListener(this);
        tv_start_time2.setOnClickListener(this);
        tv_start_time3.setOnClickListener(this);
        tv_start_time4.setOnClickListener(this);
        tv_start_time_dinner.setOnClickListener(this);

        tv_end_time1.setOnClickListener(this);
        tv_end_time2.setOnClickListener(this);
        tv_end_time3.setOnClickListener(this);
        tv_end_time4.setOnClickListener(this);
        tv_end_time_dinner.setOnClickListener(this);

        btn_savetime.setOnClickListener(this);
    }

    @Override
    protected void formatData() {
        loadData();
    }

    @Override
    protected void getBundle(Bundle bundle) {
        School_id = bundle.getString("School_id");
        type = bundle.getInt("type");
        sortTypeBeans = bundle.getParcelableArrayList("sortTypeBeans");
    }
    private void loadData(){
        Map<String,Object> map = new HashMap<>();
        map.put("school_id",School_id);
        if(type == 1){
            map.put("parent_type",11);
        }else {
            map.put("parent_type",12);
        }
        map.put("type",(type+1)+","+(type+2)+","+(type+3)+","+(type+4));
        netUtils.post("changestoresort/get_type_time",map);
    }
    private void SavetimeData(){
        Map<String,Object> map = new HashMap<>();
        map.put("school_id",School_id);
        List<SortTypeBean> typeBeans = new ArrayList<>();
        SortTypeBean bean1 = new SortTypeBean();
        SortTypeBean bean2 = new SortTypeBean();
        SortTypeBean bean3 = new SortTypeBean();
        SortTypeBean bean4 = new SortTypeBean();
        bean1.setType(type+1);
        bean1.setStart_time(tv_start_time1.getText().toString());
        bean1.setEnd_time(tv_end_time1.getText().toString());

        bean2.setType(type+2);
        bean2.setStart_time(tv_start_time2.getText().toString());
        bean2.setEnd_time(tv_end_time2.getText().toString());

        bean3.setType(type+3);
        bean3.setStart_time(tv_start_time3.getText().toString());
        bean3.setEnd_time(tv_end_time3.getText().toString());

        bean4.setType(type+4);
        bean4.setStart_time(tv_start_time4.getText().toString());
        bean4.setEnd_time(tv_end_time4.getText().toString());

        typeBeans.add(bean1);
        typeBeans.add(bean2);
        typeBeans.add(bean3);
        typeBeans.add(bean4);
        map.put("time_list",new Gson().toJson(typeBeans));
        if(type == 1){
            map.put("parent_type",11);
        }else {
            map.put("parent_type",12);
        }

        netUtils.post("changestoresort/update_type_time",map);
    }
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("School_id", School_id);

        if (pvCustomLunar == null) {
            initLunarPicker();
        }
        switch (view.getId()) {
            case R.id.btn_bianji_auto:
                bundle.putInt("type", type+1);
                Logger.i("type--->",type+"");
                bundle.putString("title", "早餐");
                bundle.putString("start_time", tv_start_time1.getText().toString());
                bundle.putString("end_time", tv_end_time1.getText().toString());
                jumpTo(SortActivity.class, bundle);
                break;
            case R.id.btn_bianji_auto_zheng:
                bundle.putInt("type", type+2);
                Logger.i("type--->",type+"");
                bundle.putString("title", "正餐");
                bundle.putString("start_time", tv_start_time2.getText().toString());
                bundle.putString("end_time", tv_end_time2.getText().toString());
                jumpTo(SortActivity.class, bundle);
                break;
            case R.id.btn_bianji_auto_xiawu:
                bundle.putInt("type", type+3);
                Logger.i("type--->",type+"");
                bundle.putString("title", "下午茶");
                bundle.putString("start_time", tv_start_time3.getText().toString());
                bundle.putString("end_time", tv_end_time3.getText().toString());
                jumpTo(SortActivity.class, bundle);
                break;
            case R.id.btn_bianji_auto_yexiao_dinner:
                bundle.putInt("type", type+3);
                Logger.i("type--->",type+"");
                bundle.putString("title", "晚餐");
                bundle.putString("start_time", tv_start_time_dinner.getText().toString());
                bundle.putString("end_time", tv_end_time_dinner.getText().toString());
                jumpTo(SortActivity.class, bundle);
                break;
            case R.id.btn_bianji_auto_yexiao:
                bundle.putInt("type", type+4);
                Logger.i("type--->",type+"");
                bundle.putString("title", "夜宵");
                bundle.putString("start_time", tv_start_time4.getText().toString());
                bundle.putString("end_time", tv_end_time4.getText().toString());
                jumpTo(SortActivity.class, bundle);
                break;
            case R.id.tv_start_time1:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_start_time2:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_start_time3:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_start_time_dinner:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_start_time4:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_end_time1:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_end_time2:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_end_time3:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_end_time_dinner:
                pvCustomLunar.show(view);
                break;
            case R.id.tv_end_time4:
                pvCustomLunar.show(view);
                break;
            case R.id.btn_savetime:
                /**
                 * todo 保存时间段
                 */
                SavetimeData();
                break;
        }

    }

    /**
     * 时间初始化
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        endDate.set(Calendar.HOUR_OF_DAY, 24);
        pvCustomLunar = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setText(getTime(date, "HH:mm"));
                    switch (v.getId()) {
                        case R.id.tv_start_time1:
                            tv_end_time4.setText(tv.getText().toString());
                            break;
                        case R.id.tv_start_time2:
                            tv_end_time1.setText(tv.getText().toString());
                            break;
                        case R.id.tv_start_time3:
                            tv_end_time2.setText(tv.getText().toString());
                            break;
//                        case R.id.tv_start_time_dinner:
//                            tv_end_time3.setText(tv.getText().toString());
//                            break;
//                        case R.id.tv_start_time4:
//                            tv_end_time_dinner.setText(tv.getText().toString());
//                            break;
                        case R.id.tv_start_time4:
                            tv_end_time3.setText(tv.getText().toString());
                            break;

                        case R.id.tv_end_time1:
                            tv_start_time2.setText(tv.getText().toString());
                            break;
                        case R.id.tv_end_time2:
                            tv_start_time3.setText(tv.getText().toString());
                            break;

                        case R.id.tv_end_time3:
                            tv_start_time4.setText(tv.getText().toString());
                            break;
//                        case R.id.tv_end_time3:
//                            tv_start_time_dinner.setText(tv.getText().toString());
//                            break;
//                        case R.id.tv_end_time_dinner:
//                            tv_start_time4.setText(tv.getText().toString());
//                            break;
                        case R.id.tv_end_time4:
                            tv_start_time1.setText(tv.getText().toString());
                            break;

                    }
                    ///EventBus.getDefault().post(new OrderRefreshByDateEntity(getTime(date,"yyyy-MM-dd")));
                }
            }
        }).setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvCancel = v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#333333"))
                .build();
    }

    private String getTime(Date date, String fomat) {
        SimpleDateFormat format = new SimpleDateFormat(fomat);
        return format.format(date);
    }

    private NetUtils.NetRequestCallBack netRequestCallBack = new NetUtils.NetRequestCallBack() {
        @Override
        public void success(String action, BaseBean t, Map tag) {
            switch (action){
                case "changestoresort/update_type_time":
                    if(t.ok == 1){
                        toast("保存时间段成功");
                        finish();
                    }else {
                        toast(t.getMsg());
                    }
                    break;
                default:
                    sortTypeBeans = (ArrayList<SortTypeBean>) GsonUtils.getInstance(mActivity).parseNoHeaderJArray(t,SortTypeBean.class);
                    initData(sortTypeBeans);
                    break;
            }
        }

        @Override
        public void error(String action, Throwable e, Map tag) {
                toast(e+"");
        }
    };

    private void initData(ArrayList<SortTypeBean> sortTypeBeans) {
        tv_start_time1.setText(sortTypeBeans.get(0).getStart_time());
        tv_start_time2.setText(sortTypeBeans.get(1).getStart_time());
        tv_start_time3.setText(sortTypeBeans.get(2).getStart_time());
        tv_start_time4.setText(sortTypeBeans.get(3).getStart_time());

        tv_end_time1.setText(sortTypeBeans.get(0).getEnd_time());
        tv_end_time2.setText(sortTypeBeans.get(1).getEnd_time());
        tv_end_time3.setText(sortTypeBeans.get(2).getEnd_time());
        tv_end_time4.setText(sortTypeBeans.get(3).getEnd_time());

    }
}
