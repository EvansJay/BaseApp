package ui.activity.goods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.ClassifyManagerAdapter;
import base.BaseBean;


/**
 * 商品-管理分类
 * @author Andlei
 * @date 2019/9/15.
 */
public class ClassifyManagerActivity extends activity.BaseLayoutActivity {
    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private LinearLayout layout_add;
    private List<BaseBean> list = new ArrayList<>();
    private ClassifyManagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("管理分类",true);
        setBaseRightText("排序");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify_manager;
    }

    @Override
    protected void findViews() {
        swipeMenuRecyclerView = getView(R.id.recy_list_);
        layout_add = getView(R.id.layout_add);
    }

    @Override
    protected void formatViews() {
        layout_add.setOnClickListener(this);
    }

    @Override
    protected void formatData() {

        for (int i =0; i<15; i++){
            BaseBean bean = new BaseBean();
            bean.setMsg("分类"+i);
            list.add(bean);
        }
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new ClassifyManagerAdapter(R.layout.item_drag_touch,list,swipeMenuRecyclerView);
        swipeMenuRecyclerView.setOnItemMoveListener(onItemMoveListener);
        swipeMenuRecyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_go_up:
                        // TODO 置顶
                        BaseBean bean = list.get(position);
                        list.remove(position);
                        list.add(0,bean);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_edit:
                        // TODO 编辑
                        Bundle bundle = new Bundle();
                        // 传值ID bundle.putString("",);
                        jumpTo(AddOrEditClassifyActivity.class,bundle);
                        break;
                }
            }
        });
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.base_right_text:
                // todo 排序
                TextView tv = (TextView) view;
                if(tv.getText().toString().equals("排序")){
                    setBaseRightText("完成");
                    for (BaseBean bean : list){
                        bean.Select = true;
                    }
                }else {
                    setBaseRightText("排序");
                    for (BaseBean bean : list){
                        bean.Select = false;
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.layout_add:
                // todo 新增分类
                jumpTo(AddOrEditClassifyActivity.class);
                break;
        }
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
            Collections.swap(list, fromPosition, toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);
            //isUpdate = true;
            //setSortGoods(getListDate(goodsList));
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            list.remove(position);
            adapter.notifyItemRemoved(position);
        }

    };
}
