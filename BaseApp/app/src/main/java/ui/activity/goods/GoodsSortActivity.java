package ui.activity.goods;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import adapter.GoodsLeftAdapter;
import adapter.GoodsNewSortRightAdapter;
import adapter.GoodsSortRightAdapter;
import adapter.OnRecyclerViewListener;

import bean.CategoryEntity;
import bean.GoodsRightEntity;
import bean.StoreBean;


/**
 * 商品排序
 * Created by soubike on 2018/7/12.
 */

public class GoodsSortActivity extends activity.BaseLayoutActivity {

//    private ActivityGoodsSortBinding mBinding;
    private RecyclerView recycler_view_left;
    private SwipeMenuRecyclerView recycler_view_right;

    private TextView tv_clazz_title,tv_auto_sort;
    private GoodsLeftAdapter mGoodsLeftAdapter;
    private GoodsNewSortRightAdapter mGoodsRightAdapter;
    private List<CategoryEntity.CategoryListBean> categoryList = new ArrayList<>();
    private List<GoodsRightEntity.GoodsListBean> goodsList = new ArrayList<>();
    private int leftChoosePosition = -1;

//    public static void start(Context context) {
//        Intent intent = new Intent(context, GoodsSortActivity.class);
//        context.startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("商品排序",true);
        setBaseRightText("保存");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_sort;
    }

    @Override
    protected void findViews() {
        tv_clazz_title = getView(R.id.tv_clazz_title);
        tv_auto_sort = getView(R.id.tv_auto_sort);
        recycler_view_left = getView(R.id.recycler_view_left);
        recycler_view_right = getView(R.id.recycler_view_right);
    }

    @Override
    protected void formatViews() {
        tv_auto_sort.setOnClickListener(this);
        recycler_view_left.setLayoutManager(new LinearLayoutManager(this));
        recycler_view_right.setLayoutManager(new LinearLayoutManager(this));
        for (int i =0;i<10;i++){
            CategoryEntity.CategoryListBean bean = new CategoryEntity.CategoryListBean();
            bean.setName("分类"+i);
            categoryList.add(bean);
        }
        for (int i = 0; i<10;i++){
            GoodsRightEntity.GoodsListBean bean = new GoodsRightEntity.GoodsListBean();
            bean.setName("商品"+i);
            goodsList.add(bean);
        }
        mGoodsLeftAdapter = new GoodsLeftAdapter(R.layout.item_left_class,categoryList);
        mGoodsRightAdapter = new GoodsNewSortRightAdapter(R.layout.item_goods_sort_right,goodsList,recycler_view_right);

        recycler_view_left.setAdapter(mGoodsLeftAdapter);
        recycler_view_right.setAdapter(mGoodsRightAdapter);

        recycler_view_right.setOnItemMoveListener(onItemMoveListener);

        mGoodsLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tv_clazz_title.setText(categoryList.get(position).getName().concat("("+ goodsList.size() +")"));
                for (CategoryEntity.CategoryListBean bean : categoryList){
                    bean.isSelect = false;
                }
                categoryList.get(position).isSelect = true;
                adapter.notifyDataSetChanged();
                requestRightDate(categoryList.get(position).getId());
            }
        });
        mGoodsRightAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_go_up:
                        GoodsRightEntity.GoodsListBean bean = goodsList.get(position);
                        goodsList.remove(position);
                        goodsList.add(0,bean);
                        adapter.notifyDataSetChanged();
                        break;

                }
            }
        });
    }

    @Override
    protected void formatData() {

    }

    public void initListener() {
//        mBinding.tvAutoSort.setOnClickListener(this);
//        mBinding.recyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
//        mBinding.recyclerViewRight.setLayoutManager(new LinearLayoutManager(this));
//
//        mGoodsLeftAdapter = new GoodsLeftAdapter(categoryList);
//        mGoodsRightAdapter = new GoodsSortRightAdapter(goodsList,mBinding.recyclerViewRight);
//
//        mBinding.recyclerViewLeft.setAdapter(mGoodsLeftAdapter);
//        mBinding.recyclerViewRight.setAdapter(mGoodsRightAdapter);
//
//        mBinding.recyclerViewRight.setOnItemMoveListener(onItemMoveListener);
//
//        mGoodsLeftAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
//            @Override
//            public void onItemClick(View itemView, int position) {
//                mBinding.tvClazzTitle.setText(categoryList.get(position).getName().concat("("+ goodsList.size() +")"));
//                leftChoosePosition = position;
//                requestRightDate(categoryList.get(position).getId());
//            }
//        });

    }


    protected void initView(Bundle savedInstanceState) {
//        mBinding = DataBindingUtil.setContentView(this,getLayoutId());
//        mBinding.setPage(this);
        initTitle();
        initData();
    }

    private void initTitle() {
//        mBinding.title.tvLeft.setVisibility(View.VISIBLE);
//        mBinding.title.tvTitle.setText("商品排序");
//        mBinding.title.tvRight.setVisibility(View.VISIBLE);
//        mBinding.title.tvRight.setText("保存");
//
//        mBinding.title.tvLeft.setOnClickListener(this);
//        mBinding.title.tvRight.setOnClickListener(this);

    }

    private void initData() {

        requestLeftData();

    }

    /**
     * 获取商品列表
     * @param cid
     */
    private void requestRightDate(String cid) {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().getGoodsListByCategory(cid,"0"), new OnSuccessCallBack<GoodsRightEntity>() {
//            @Override
//            public void onSuccess(GoodsRightEntity entity) {
//
//                goodsList.clear();
//                goodsList.addAll(entity.getGoods_list());
//                mGoodsRightAdapter.notifyDataSetChanged();
//                if (goodsList.size() <= 0){
//                    TextUtils.makeText("该分类下暂无商品");
//                }
//
//                mBinding.tvClazzTitle.setText(categoryList.get(leftChoosePosition == -1 ? 0:leftChoosePosition).getName().concat("("+ goodsList.size() +")"));
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }

    /**
     * 分类列表
     */
    private void requestLeftData() {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().getCategoryList(), new OnSuccessCallBack<CategoryEntity>() {
//            @Override
//            public void onSuccess(CategoryEntity entity) {
//                categoryList.clear();
//                categoryList.addAll(entity.getCategory_list());
//                mGoodsLeftAdapter.notifyDataSetChanged();
//
//                if (leftChoosePosition < 0){
//                    if(categoryList.size()<=0)return;
//                    requestRightDate(categoryList.get(0).getId()); //init right data
//                }else {
//                    if(categoryList.size()<=0)return;
//                    requestRightDate(categoryList.get(leftChoosePosition).getId()); //init right data
//                }
//
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }


    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();

            // Item被拖拽时，交换数据，并更新adapter。
            Collections.swap(goodsList, fromPosition, toPosition);
            mGoodsRightAdapter.notifyItemMoved(fromPosition, toPosition);

            setSortGoods(getListDate(goodsList),false);
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            goodsList.remove(position);
            mGoodsRightAdapter.notifyItemRemoved(position);
        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                setSortGoods(getListDate(goodsList),true);

                break;

//            case R.id.tv_auto_sort:
//
//                shotByVolume();
//
//                break;

            default:
                break;
        }
    }


    private void setSortGoods(String sortinfo, final boolean isfinish){
//        HttpServer.getDataFromServer(RequestParams.getInstance().setSortGoods(sortinfo), new OnSuccessCallBack<Object>() {
//            @Override
//            public void onSuccess(Object entity) {
//                if(isfinish){
//                    finish();
//                }
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }

    /**
     * 根据销量排序
     */
    private void shotByVolume() {
        Collections.sort(goodsList);
        mGoodsRightAdapter.notifyDataSetChanged();


        setSortGoods(getListDate(goodsList),false);


    }

    private String getListDate(List<GoodsRightEntity.GoodsListBean> list){
        StringBuffer sortInfo = new StringBuffer();

        sortInfo.append("[");
        for (int i = 0; i < list.size(); i++) {
            sortInfo.append("{");
            sortInfo.append("\"gid\":" + list.get(i).getGoods_id() + ",");
            sortInfo.append("\"sort\":" + (i+1));
            sortInfo.append("}");
            sortInfo.append(",");
        }
        sortInfo.deleteCharAt(sortInfo.length() -1);
        sortInfo.append("]");

        return sortInfo.toString();
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }
}
