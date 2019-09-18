package ui.activity.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.List;
import adapter.GoodsLeftAdapter;
import adapter.GoodsRightAdapter;
import bean.CategoryEntity;
import bean.GoodsRightEntity;
import ui.activity.goods.ClassifyManagerActivity;
import ui.activity.goods.GoodsSortActivity;
import widget.ClearEditText;
import widget.GoodsScreenPopupWindow;


/**
 * 全部商品
 * Created by soubike on 2018/7/12.
 */

public class GoodsEditActivity extends activity.BaseLayoutActivity {


    private ClearEditText edittext_search;
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    private GoodsLeftAdapter mGoodsLeftAdapter;
    private GoodsRightAdapter mGoodsRightAdapter;
    private TextView tvChoose;
    private ImageView ivBack;
    private GoodsScreenPopupWindow goodsScreenPopupWindow;
    private View tvNewGoods;
    private View tvPaixu;
    private View tvManagerClass;
    private List<CategoryEntity.CategoryListBean> categoryList = new ArrayList<>();
    private List<GoodsRightEntity.GoodsListBean> goodsList = new ArrayList<>();
    private int leftChoosePosition = 0;
    private TextView tvClazzTitle;
    private String status = "0";




    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_edit;
    }

    @Override
    protected void findViews() {
        edittext_search = getView(R.id.edittext_search);
        recyclerViewLeft = getView(R.id.recycler_view_left);
        recyclerViewRight = getView(R.id.recycler_view_right);
        tvManagerClass = getView(R.id.tv_manager_class);
        tvPaixu = getView(R.id.tv_paixu);
        tvNewGoods = getView(R.id.tv_new_goods);
        tvChoose = getView(R.id.tv_choose);
        ivBack = getView(R.id.iv_back);
        tvClazzTitle = getView(R.id.tv_clazz_title);
    }

    @Override
    protected void formatViews() {
        ivBack.setOnClickListener(this);
        tvManagerClass.setOnClickListener(this);
        tvPaixu.setOnClickListener(this);
        tvNewGoods.setOnClickListener(this);

        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRight.setLayoutManager(new LinearLayoutManager(this));

        tvChoose.setOnClickListener(this);

        for (int i = 0;i<10;i++){
            CategoryEntity.CategoryListBean listBean = new CategoryEntity.CategoryListBean();
            listBean.setName("种类"+i);
            categoryList.add(listBean);
            GoodsRightEntity.GoodsListBean bean = new GoodsRightEntity.GoodsListBean();
            bean.setName("商品"+i);
            goodsList.add(bean);
        }
        mGoodsLeftAdapter = new GoodsLeftAdapter(R.layout.item_left_class,categoryList);
        mGoodsRightAdapter = new GoodsRightAdapter(R.layout.item_right_class,goodsList);

        recyclerViewLeft.setAdapter(mGoodsLeftAdapter);
        recyclerViewRight.setAdapter(mGoodsRightAdapter);

        mGoodsLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tvClazzTitle.setText(categoryList.get(position).getName().concat("("+ goodsList.size() +")"));
                //leftChoosePosition = position;
                for (CategoryEntity.CategoryListBean bean : categoryList){
                    bean.isSelect = false;
                }
                categoryList.get(position).isSelect = true;
                mGoodsLeftAdapter.notifyDataSetChanged();
                requestRightDate(categoryList.get(leftChoosePosition).getId());
            }
        });
        mGoodsRightAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.btn_edit:
                        // TODo 跳转至 商品详情页面
                        Bundle bundle = new Bundle();
                        bundle.putString("gid",goodsList.get(position).getGoods_id());
                        jumpTo(EditDetailActivity.class,bundle);
                        break;
                    case R.id.btn_up_down:
                        GoodsRightEntity.GoodsListBean goodsListBean = goodsList.get(position);
                        if (goodsListBean.getStatus() == 1){
                            changeStautsGoods(goodsListBean.getGoods_id(),"0",position);//下架
                        }else {
                            changeStautsGoods(goodsListBean.getGoods_id(),"1",position);
                        }

                        break;
                }
            }
        });

        edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO 搜索商品

            }
        });
    }

    @Override
    protected void formatData() {
        requestLeftData();
    }


    /**
     * 下架 上架
     */
    private void changeStautsGoods(String gid, final String type, final int position) {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().changeStautsGoods(gid,type), new OnSuccessCallBack<Object>() {
//            @Override
//            public void onSuccess(Object entity) {
//                if (type.equals("0")){
//                    goodsList.get(position).setStatus(0);
//                }else {
//                    goodsList.get(position).setStatus(1);
//                }
//                mGoodsRightAdapter.notifyDataSetChanged();
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
     * 获取商品列表
     * @param cid
     */
    private void requestRightDate(String cid) {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().getGoodsListByCategory(cid,status), new OnSuccessCallBack<GoodsRightEntity>() {
//            @Override
//            public void onSuccess(GoodsRightEntity entity) {
//
//                goodsList.clear();
//                goodsList.addAll(entity.getGoods_list());
//                mGoodsRightAdapter.notifyDataSetChanged();
//                if (goodsList.size() <= 0){
//                    TextUtils.makeText("暂无商品");
//                }
//
//                tvClazzTitle.setText(categoryList.get(leftChoosePosition).getName().concat("("+ goodsList.size() +")"));
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_choose:
                goodsScreenPopupWindow = new GoodsScreenPopupWindow(this, this);
                goodsScreenPopupWindow.showAtLocation(findViewById(R.id.ll_content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.tv_all:
                tvChoose.setText("全部商品");
                status = "0";
                if(categoryList.size()>0){
                    requestRightDate(categoryList.get(leftChoosePosition).getId());
                }

                goodsScreenPopupWindow.hiddenPopupWindow();
                break;
            case R.id.tv_up:
                tvChoose.setText("已上架");
                status = "1";
                if(categoryList.size()>0){
                    requestRightDate(categoryList.get(leftChoosePosition).getId());
                }

                goodsScreenPopupWindow.hiddenPopupWindow();
                break;
            case R.id.tv_down:
                tvChoose.setText("已下架");
                status = "2";
                if(categoryList.size()>0){
                    requestRightDate(categoryList.get(leftChoosePosition).getId());
                }
                goodsScreenPopupWindow.hiddenPopupWindow();
                break;
            case R.id.tv_manager_class:  //管理分类
                // TODO CategoryManagerActivity.start(this);
                jumpTo(ClassifyManagerActivity.class);
                break;

            case R.id.tv_paixu:     //商品排序
                // TODO  GoodsSortActivity.start(this);
                jumpTo(GoodsSortActivity.class);
                break;

            case R.id.tv_new_goods:  //新建商品
                // TODO  EditDetailActivity.start(mContext);
                jumpTo(EditDetailActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }
}
