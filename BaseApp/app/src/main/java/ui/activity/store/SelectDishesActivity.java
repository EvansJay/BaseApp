package ui.activity.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import adapter.GoodsOnOffListAdapter;

import bean.StoreBean;
import widget.ClearEditText;

/**
 * 选择菜品
 *
 * @author Andlei
 * @date 2019/9/10.
 */
public class SelectDishesActivity extends activity.BaseLayoutActivity {
    private ClearEditText edittext_search_shopname;
    private RecyclerView recy_list_dishes;
    private GoodsOnOffListAdapter adapter;
    private List<StoreBean> finalstorelist = new ArrayList<>();
    private List<StoreBean> list = new ArrayList<>();
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择菜品", true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_dishes;
    }

    @Override
    protected void findViews() {
        edittext_search_shopname = getView(R.id.edittext_search_shopname);
        recy_list_dishes = getView(R.id.recy_list_dishes);
        btn_save = getView(R.id.btn_save);
    }

    @Override
    protected void formatViews() {
        btn_save.setOnClickListener(this);
        edittext_search_shopname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO 搜索菜品
                list.clear();
                if (!TextUtils.isEmpty(editable.toString())) {
                    for (StoreBean bean : finalstorelist) {
                        if (bean.getShop_name().contains(editable.toString().trim())) {
                            list.add(bean);
                        }
                    }
                } else {
                    list.addAll(finalstorelist);
                }
                adapter.setNewData(list);
            }
        });
    }

    @Override
    protected void formatData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recy_list_dishes.setLayoutManager(layoutManager);
        for (int i = 0; i < 10; i++) {
            StoreBean bean = new StoreBean();
            bean.setShop_name("商品名称" + i);
            list.add(bean);
            finalstorelist.add(bean);
        }
        adapter = new GoodsOnOffListAdapter(R.layout.item_onoff_goods, list);
        recy_list_dishes.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int i = 0;
                for (StoreBean bean : list) {
                    if (bean.getIs_open() == 1) {
                        i++;
                    }
                }
                if (list.get(position).getIs_open() == 1) {
                    list.get(position).setIs_open(0);
                } else {
                    if (i >= 3) {
                        toast("最多开启三个首页广告推荐位");
                    } else {
                        list.get(position).setIs_open(1);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                adapter.notifyDataSetChanged();

                break;
        }
    }
}
