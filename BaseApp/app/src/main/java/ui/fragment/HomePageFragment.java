package ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import adapter.HomepageAdapter;
import base.fragment.BaseFragment;
import bean.HomePageBean;
import ui.activity.order.GroupBuyOrderActivity;
import ui.activity.order.OrderListActivity;
import ui.activity.rider.DistributionSettingActivity;
import ui.activity.store.AddIntelligenceActivity;
import ui.activity.store.AddShopActivity;
import ui.activity.store.GoodsManagerActivity;
import ui.activity.store.IntelligenceListActivity;
import ui.activity.store.OnOffStoreActivity;
import ui.activity.store.PosterManagerActivity;
import ui.activity.store.SortActivity;
import ui.activity.store.StoreListActivity;
import ui.activity.store.StoreSortActivity;
import ui.activity.store.TrimStoreActivity;

/**
 * @author Andlei
 * @date 2019/6/24.
 */
public class HomePageFragment extends BaseFragment {
    private HomepageAdapter adapter;
    private HomepageAdapter adapter_order,adapter_rider;
    private List<HomePageBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<HomePageBean> list_order = new ArrayList<>();
    private RecyclerView recyclerView_order;
    private List<HomePageBean> list_rider = new ArrayList<>();
    private RecyclerView recyclerView_rider;
    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setTitle("首页",false);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void findViews() {
        recyclerView = getView(R.id.rcly_glist);
        recyclerView_order = getView(R.id.rcly_glist_order);
        recyclerView_rider = getView(R.id.rcly_glist_ridermanager);
    }

    @Override
    protected void formatViews() {
        HomePageBean homePageBean = new HomePageBean();
        homePageBean.imgid = R.mipmap.icon_sort;
        homePageBean.title= "店铺排序";

        HomePageBean homePageBean1 = new HomePageBean();
        homePageBean1.imgid = R.mipmap.icon_onoff;
        homePageBean1.title= "店铺列表";

        HomePageBean homePageBean2 = new HomePageBean();
        homePageBean2.imgid = R.mipmap.icon_adjust;
        homePageBean2.title= "调整配送";

        HomePageBean homePageBean3 = new HomePageBean();
        homePageBean3.imgid = R.mipmap.icon_poster;
        homePageBean3.title= "广告管理";
//
//        HomePageBean homePageBean4 = new HomePageBean();
//        homePageBean4.imgid = R.mipmap.icon_intelligence_list;
//        homePageBean4.title= "资质列表";
//
//        HomePageBean homePageBean5 = new HomePageBean();
//        homePageBean5.imgid = R.mipmap.icon_intelligence_add;
//        homePageBean5.title= "资质添加";
        list.add(homePageBean);
        list.add(homePageBean1);
        list.add(homePageBean2);
        list.add(homePageBean3);
//        list.add(homePageBean3);
//        list.add(homePageBean4);
//        list.add(homePageBean5);

//        HomePageBean homePageBeano = new HomePageBean();
//        homePageBeano.imgid = R.mipmap.icon_orderlist;
//        homePageBeano.title= "订单列表";
//        HomePageBean homePageBeano1 = new HomePageBean();
//        homePageBeano1.imgid = R.mipmap.icon_groupbug;
//        homePageBeano1.title= "团购订单";
//        list_order.add(homePageBeano);
//        list_order.add(homePageBeano1);

        HomePageBean rhomePageBean = new HomePageBean();
        rhomePageBean.imgid = R.mipmap.icon_peisong_seting;
        rhomePageBean.title= "配送设置";
//        HomePageBean rhomePageBean1 = new HomePageBean();
//        rhomePageBean.imgid = R.mipmap.icon_groupbug;
//        rhomePageBean1.title= "团购订单";
        list_rider.add(rhomePageBean);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomepageAdapter(R.layout.item_homepage,list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        jumpTo(StoreSortActivity.class);
                        break;
                    case 1:
                        jumpTo(StoreListActivity.class);
                        break;
                    case 2:
                        jumpTo(TrimStoreActivity.class);
                        break;
                    case 3:
                        //toast("正在开发中,敬请期待...");
                        jumpTo(PosterManagerActivity.class);
                        break;
                    case 4:
                        toast("正在开发中,敬请期待...");
//                        jumpTo(IntelligenceListActivity.class);
                        break;
                    case 5:
                        toast("正在开发中,敬请期待...");
//                        jumpTo(AddIntelligenceActivity.class);
                        break;
                    default:
                        toast("正在开发中,敬请期待...");
                        break;
                }
            }
        });
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),4);
        adapter_order = new HomepageAdapter(R.layout.item_homepage,list_order);
        recyclerView_order.setLayoutManager(layoutManager1);
        recyclerView_order.setAdapter(adapter_order);
        adapter_order.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        toast("正在开发中,敬请期待...");
                        //jumpTo(OrderListActivity.class);
                        break;
                    case 1:
                        toast("正在开发中,敬请期待...");
                        //jumpTo(GroupBuyOrderActivity.class);
                        break;
                }
            }
        });
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),4);
        adapter_rider = new HomepageAdapter(R.layout.item_homepage,list_rider);
        recyclerView_rider.setLayoutManager(layoutManager2);
        recyclerView_rider.setAdapter(adapter_rider);
        adapter_rider.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        jumpTo(DistributionSettingActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {

    }
}
