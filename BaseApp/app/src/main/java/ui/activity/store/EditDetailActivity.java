package ui.activity.store;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import base.activity.BaseLayoutActivity;
import bean.CategoryEntity;
import bean.GoodsDetailsEntity;
import bean.StoreBoxEntity;
import event.AttrEvent;
import event.SpecEvent;
import me.iwf.photopicker.PhotoPicker;
import ui.activity.goods.AttributesActivity;
import ui.activity.goods.MostSpecActivity;
import utils.GlideUtils;
import widget.HiDialog;

/**
 * 商品编辑页面
 * Created by soubike on 2018/7/28.
 */

public class EditDetailActivity extends BaseLayoutActivity {
    private ImageView iv_pic;
    private EditText et_goods_name,et_goods_desc;
    private TextView tv_goods_clazz,tv_num_clean,tv_num_max;
    private EditText et_price,et_num,et_num_max;
    private RecyclerView recycler_view_spec,recycler_view_attr;
    private TextView tv_most_spec,tv_most_attr,tv_delete,et_box_price;
    private GoodsDetailsEntity mDetailsEntity;
    private String mGid;
    private CategoryEntity mCategroyEntity;  //分类entity
    private List<StoreBoxEntity> storeBoxEntities = new ArrayList<>();
    private int yourChoice; //分类选择i
    private int yourChoice_box; //餐盒费选择
    private AlertDialog.Builder singleChoiceDialog;

    private ArrayList<GoodsDetailsEntity.GoodsDetailBean.SpenBean> specBeanList = new ArrayList<>();
    private ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> attrBeanList = new ArrayList<>();
    private SimpleSpecAdapter simpleSpecAdapter;
    private SimpleAttrAdapter simpleAttrAdapter;
    private String category_id;
    private String box_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    public static void start(Context mContext) {
//        mContext.startActivity(new Intent(mContext,EditDetailActivity.class));
//    }
//
//    public static void start(Context mContext,String gid) {
//        Intent intent = new Intent(mContext, EditDetailActivity.class);
//        intent.putExtra("gid",gid);
//        mContext.startActivity(intent);
//    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_detail;
    }

    @Override
    protected void findViews() {
        iv_pic = getView(R.id.iv_pic);
        et_goods_name = getView(R.id.et_goods_name);
        et_goods_desc = getView(R.id.et_goods_desc);
        tv_goods_clazz = getView(R.id.tv_goods_clazz);
        et_price = getView(R.id.et_price);
        et_box_price = getView(R.id.et_box_price);
        et_num = getView(R.id.et_num);
        et_num_max = getView(R.id.et_num_max);
        tv_num_clean = getView(R.id.tv_num_clean);
        tv_num_max = getView(R.id.tv_num_max);
        recycler_view_spec = getView(R.id.recycler_view_spec);
        recycler_view_attr = getView(R.id.recycler_view_attr);
        tv_most_spec = getView(R.id.tv_most_spec);
        tv_most_attr = getView(R.id.tv_most_attr);
        tv_delete = getView(R.id.tv_delete);
    }

    @Override
    protected void formatViews() {
        iv_pic.setOnClickListener(this);
        tv_goods_clazz.setOnClickListener(this);
        tv_num_clean.setOnClickListener(this);
        tv_num_max.setOnClickListener(this);
        tv_most_spec.setOnClickListener(this);
        tv_most_attr.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        et_box_price.setOnClickListener(this);
        if (!EventBus.getDefault().isRegistered(this)){

            //注册事件
            EventBus.getDefault().register(this);
        }


        initTitle();
        initData();
        initRecyclerView();
    }

    @Override
    protected void formatData() {

    }




    private void initRecyclerView() {
        recycler_view_spec.setLayoutManager(new LinearLayoutManager(this));
        recycler_view_attr.setLayoutManager(new LinearLayoutManager(this));

        simpleSpecAdapter = new SimpleSpecAdapter();
        simpleAttrAdapter = new SimpleAttrAdapter();
        recycler_view_spec.setAdapter(simpleSpecAdapter);
        recycler_view_attr.setAdapter(simpleAttrAdapter);

        //禁用滑动
        recycler_view_spec.setNestedScrollingEnabled(false);
        recycler_view_attr.setNestedScrollingEnabled(false);
    }

    protected void initData() {
        if (isEdit()) {
            requestGoodsDetial(mGid);
            setTitle("编辑商品");
        }else {
            setTitle("添加商品");

            initBean();
        }

        requestClazzList();
        requestBoxlist();
    }

    /**
     * 请求商品分类列表
     */
    private void requestClazzList() {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().getCategoryList(), new OnSuccessCallBack<CategoryEntity>() {
//
//            @Override
//            public void onSuccess(CategoryEntity entity) {
//                mCategroyEntity = entity;
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
     * 请求餐盒费
     */
    private void requestBoxlist() {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().getStoreBox(), new OnSuccessCallBack<ArrayList<StoreBoxEntity>>() {
//
//            @Override
//            public void onSuccess(ArrayList<StoreBoxEntity> entity) {
//                storeBoxEntities = entity;
//
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }
    private void initBean() {
        mDetailsEntity = new GoodsDetailsEntity();
        mDetailsEntity.setGoods_detail(new GoodsDetailsEntity.GoodsDetailBean());
    }

    /**
     * 请求商品详情
     * @param gid
     */
    private void requestGoodsDetial(String gid) {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().getGoodsDetailsByGid(gid), new OnSuccessCallBack<GoodsDetailsEntity>() {
//            @Override
//            public void onSuccess(GoodsDetailsEntity entity) {
//                mDetailsEntity = entity;
//                category_id = entity.getGoods_detail().getCategory_id();
//
//                Glide.with(EditDetailActivity.this).load(entity.getGoods_detail().getIcon()).into(mBinding.ivPic);
//                mBinding.setData(entity.getGoods_detail());
//
//                specBeanList.clear();
//                specBeanList.addAll(entity.getGoods_detail().getSpec());
//                attrBeanList.clear();
//                attrBeanList.addAll(entity.getGoods_detail().getAttr());
//
//                simpleSpecAdapter.notifyDataSetChanged();
//                simpleAttrAdapter.notifyDataSetChanged();
//
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }

    private void initTitle() {
//        mBinding.title.tvLeft.setVisibility(View.VISIBLE);
//        mBinding.title.tvLeft.setText("取消");
//        mBinding.title.tvLeft.setCompoundDrawables(null,null,null,null);
//        mBinding.title.tvRight.setVisibility(View.VISIBLE);
//        mBinding.title.tvRight.setText("保存");
//
//        mBinding.title.tvLeft.setOnClickListener(this);
//        mBinding.title.tvRight.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                try {
                    if( Double.parseDouble(et_price.getText().toString()) == 0){
                        new HiDialog.Builder(EditDetailActivity.this)
                                .setContent("价格有0元，确定保存吗？")
                                .setLeftBtnText("取消")
                                .setRightBtnText("确定")
                                .setRightCallBack(new HiDialog.RightClickCallBack() {
                                    @Override
                                    public void dialogRightBtnClick() {

                                        if (isEdit()){  //编辑商品
                                            submitData();
                                        }else {
                                            //添加商品
                                            addGoodsDate();
                                        }
                                    }
                                }).setLeftCallBack(new HiDialog.LeftClickCallBack() {
                            @Override
                            public void dialogLeftBtnClick() {
                                return;
                            }
                        }).build();
                    }else {
                        if (isEdit()){  //编辑商品
                            submitData();
                        }else {
                            //添加商品
                            addGoodsDate();
                        }
                    }
                }catch (Exception e ){
                    toast("价格输入格式有误！");
                }

                break;

            case R.id.iv_pic:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;

            case R.id.tv_goods_clazz:
                showSingleChooseDialog(tv_goods_clazz);
                break;
            case R.id.et_box_price:
                showSingleChooseDialogBox(et_box_price);
                break;
            case R.id.tv_num_clean:
                et_num.setText("");
                break;
            case R.id.tv_num_max:
                et_num.setText("10000");
                break;
            case R.id.tv_most_spec:  //添加多规格
               // MostSpecManagerActivity.start(this,specBeanList);
                jumpTo(MostSpecActivity.class);
                break;
            case R.id.tv_most_attr:  //添加属性
               // MostAttrManagerActivity.start(this,attrBeanList);
                jumpTo(AttributesActivity.class);
                break;
            case R.id.tv_delete:
                alertDialog("确定要删除商品吗");
                break;
            default:
                break;

        }
    }



    private void alertDialog(String text) {
        new HiDialog.Builder(EditDetailActivity.this)
                .setContent(text)
                .setLeftBtnText("取消")
                .setRightBtnText("立即删除")
                .setRightCallBack(new HiDialog.RightClickCallBack() {
                    @Override
                    public void dialogRightBtnClick() {
                        deleteGoods();
                    }
                }).build();

    }

    private void deleteGoods() {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().deleteGoods(mDetailsEntity.getGoods_detail().getGoods_id()), new OnSuccessCallBack() {
//            @Override
//            public void onSuccess(Object entity) {
//                TextUtils.makeText("删除成功");
//                finish();
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }

    private void addGoodsDate() {
//        String name = mBinding.etGoodsName.getText().toString();  //名称
//        String clazz = mBinding.tvGoodsClazz.getText().toString();//分类
//        String price = mBinding.etPrice.getText().toString();//价格
//        String num = mBinding.etNum.getText().toString();//库存
//        if (VerifyUtil.isEmpty(name) || VerifyUtil.isEmpty(clazz) || VerifyUtil.isEmpty(price) || VerifyUtil.isEmpty(num)){
//
//            TextUtils.makeText("请输入必填项");
//            return;
//        }
//        GoodsDetailsEntity.GoodsDetailBean detail = mDetailsEntity.getGoods_detail();
//        detail.setName(name);
//        detail.setDescription(mBinding.etGoodsDesc.getText().toString());
//        detail.setCategory_id(category_id);
//        detail.setBox_id(box_id);
//        detail.setPrice(price);
//        detail.setFood_box_price("");
//        detail.setNum(num);
//        detail.setSpec(specBeanList);
//        detail.setAttr(attrBeanList);
//
//        HttpServer.getDataFromServer(RequestParams.getInstance().addGoods(detail), new OnSuccessCallBack() {
//            @Override
//            public void onSuccess(Object entity) {
//                finish();
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }

    /**
     * 提交数据
     */
    private void submitData() {
//        String name = mBinding.etGoodsName.getText().toString();  //名称
//        String clazz = mBinding.tvGoodsClazz.getText().toString();//分类
//        String price = mBinding.etPrice.getText().toString();//价格
//        String num = mBinding.etNum.getText().toString();//库存
//        if (VerifyUtil.isEmpty(name) || VerifyUtil.isEmpty(clazz) || VerifyUtil.isEmpty(price) || VerifyUtil.isEmpty(num)){
//
//            TextUtils.makeText("请输入必填项");
//            return;
//        }
//        GoodsDetailsEntity.GoodsDetailBean detail = mDetailsEntity.getGoods_detail();
//        detail.setName(name);
//        detail.setDescription(mBinding.etGoodsDesc.getText().toString());
//        detail.setCategory_id(category_id);
//        detail.setBox_id(box_id);
//        detail.setPrice(price);
//        detail.setFood_box_price("");
//        detail.setNum(num);
//        detail.setSpec(specBeanList);
//        detail.setAttr(attrBeanList);
//
//        HttpServer.getDataFromServer(RequestParams.getInstance().updateGoods(detail), new OnSuccessCallBack() {
//            @Override
//            public void onSuccess(Object entity) {
//                finish();
//            }
//        }, new OnFailureCallBack() {
//            @Override
//            public void onFailure(int code, String msg) {
//                TextUtils.makeText(msg);
//            }
//        });
    }

    /**
     * 上传图片
     */
    private void uploadFile(File file) {
//        HttpServer.getDataFromServer(this,RequestParams.getInstance().fileUpload(file), new OnSuccessCallBack<FileUploadEntity>() {
//            @Override
//            public void onSuccess(FileUploadEntity entity) {
//
//                if (mDetailsEntity != null) mDetailsEntity.getGoods_detail().setImg_path(entity.getImg_path());
//
//                Glide.with(EditDetailActivity.this).load(entity.getSmall_img_url()).into(mBinding.ivPic);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                //本地文件
                File file = new File(photos.get(0));
                GlideUtils.loadImageView(mContext,photos.get(0),iv_pic);
                uploadFile(file);

            }
        }
    }



    private void showSingleChooseDialog(final TextView tv){

        if (mCategroyEntity == null || mCategroyEntity.getCategory_list() == null || mCategroyEntity.getCategory_list().size() <= 0){
            toast("暂无分类，请先添加分类");
            finish();
            return;
        }

        List<CategoryEntity.CategoryListBean> category_list = mCategroyEntity.getCategory_list();

        final String[] items = new String[category_list.size()];

        for (int i = 0; i < category_list.size(); i++) {
            items[i] = category_list.get(i).getName();
        }
        singleChoiceDialog = new AlertDialog.Builder(EditDetailActivity.this);
        singleChoiceDialog.setTitle("请选择分类");
        if(android.text.TextUtils.isEmpty(category_id)){
            category_id = mCategroyEntity.getCategory_list().get(0).getId();
        }
        singleChoiceDialog.setSingleChoiceItems(items, yourChoice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yourChoice = which;
                category_id = mCategroyEntity.getCategory_list().get(yourChoice).getId();
                tv.setText(items[yourChoice]);

                //TextUtils.makeText(category_id+"");
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        tv.setText(items[yourChoice]);
                    }
                });

        singleChoiceDialog.show();
    }



    private void showSingleChooseDialogBox(final TextView tv){

        if (storeBoxEntities == null ||  storeBoxEntities.size() <= 0){
            toast("暂无餐盒费列表，请联系客服");
            finish();
            return;
        }

        List<StoreBoxEntity> category_list = storeBoxEntities;

        final String[] items = new String[category_list.size()];

        for (int i = 0; i < category_list.size(); i++) {
            items[i] = category_list.get(i).getMoney();
        }
        singleChoiceDialog = new AlertDialog.Builder(EditDetailActivity.this);
        singleChoiceDialog.setTitle("请选择餐盒费");
        if(android.text.TextUtils.isEmpty(box_id)){
            box_id = storeBoxEntities.get(0).getBox_id();
        }
        singleChoiceDialog.setSingleChoiceItems(items, yourChoice_box, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yourChoice_box = which;
                box_id = storeBoxEntities.get(yourChoice_box).getBox_id();
                tv.setText(items[yourChoice_box]);
//                TextUtils.makeText(box_id+"");
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tv.setText(items[yourChoice_box]);
            }
        });

        singleChoiceDialog.show();
    }

    /**
     * 根据是否有gid 判断当前页面是编辑商品还是 添加商品
     * @return
     */
    private boolean isEdit(){

        if (TextUtils.isEmpty(mGid)) {
            return false;
        }

        return true;
    }

    private class SimpleSpecAdapter extends RecyclerView.Adapter<SimpleSpecAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleSpecAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_simple_spec, parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_spec_name.setText(specBeanList.get(position).getName() + " " + specBeanList.get(position).getPrice() + " 元");
            holder.tv_spec_num.setText("");
        }

        @Override
        public int getItemCount() {
            return specBeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tv_spec_name,tv_spec_num;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_spec_name = itemView.findViewById(R.id.tv_spec_name);
                tv_spec_num = itemView.findViewById(R.id.tv_spec_num);
            }
        }
    }

    private class SimpleAttrAdapter extends RecyclerView.Adapter<SimpleAttrAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleAttrAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_simple_attr, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvAttrName.setText(attrBeanList.get(position).getName());
            holder.tvAttrValue.setText(attrBeanList.get(position).getItem());
        }

        @Override
        public int getItemCount() {
            return attrBeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvAttrName,tvAttrValue;

            public ViewHolder(View itemView) {
                super(itemView);
                tvAttrName = itemView.findViewById(R.id.tv_attr_name);
                tvAttrValue = itemView.findViewById(R.id.tv_attr_value);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void getBundle(Bundle bundle) {
        if(bundle != null){
            mGid = bundle.getString("gid","");
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotifyEvent(SpecEvent event){
        this.specBeanList.clear();
        this.specBeanList.addAll(event.getSpecBeanList());
        simpleSpecAdapter.notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAttrNotifyEvent(AttrEvent event){
        this.attrBeanList.clear();
        this.attrBeanList.addAll(event.getAttrBeanList());
        simpleAttrAdapter.notifyDataSetChanged();

    }
}
