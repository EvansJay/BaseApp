package adapter;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import base.BaseBean;

/**
 * @author Andlei
 * @date 2019/9/15.
 */
public class ClassifyManagerAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {
    private SwipeMenuRecyclerView swipeMenuRecyclerView;

    public ClassifyManagerAdapter(int layoutResId, @Nullable List<BaseBean> data,SwipeMenuRecyclerView swipeMenuRecyclerView) {
        super(layoutResId, data);
        this.swipeMenuRecyclerView = swipeMenuRecyclerView;
    }

    @Override
    protected void convert(final BaseViewHolder helper, BaseBean item) {

        helper.setText(R.id.tv_name,item.msg);
        if(item.Select){
            helper.getView(R.id.ll_button).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_edit).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.ll_button).setVisibility(View.GONE);
            helper.getView(R.id.tv_edit).setVisibility(View.VISIBLE);
        }
        helper.setOnTouchListener(R.id.iv_paixu, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        swipeMenuRecyclerView.startDrag(helper);
                        break;
                    }
                }
                return false;
            }
        });
        helper.addOnClickListener(R.id.tv_go_up);
        helper.addOnClickListener(R.id.tv_edit);
    }
}
