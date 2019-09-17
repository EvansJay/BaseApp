package widget;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import adapter.StoreOnOffListAdapter;
import bean.StoreBean;
import utils.DensityUtil;

/**
 * 选中列表弹窗
 * Created by Andlei on 2018/7/23.
 */

public class ShopSelectPopupWindow extends PopupWindow {
    private Context mContext;
    private final View mView;
    private LinearLayout mLlContent;
    private RecyclerView recyclerView;
    private StoreOnOffListAdapter adapter;
    public ShopSelectPopupWindow(Context context, BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener, final List<StoreBean> storeBeanList) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.pop_select_store,null);
        initView(mView);
        recyclerView = mView.findViewById(R.id.rcy_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoreOnOffListAdapter(R.layout.item_onoff_store,storeBeanList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(onItemChildClickListener);

    }

    private void initView(View view) {
        mLlContent = view.findViewById(R.id.pop_layout);
        this.setContentView(mView);
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //this.setHeight(DensityUtil.dip2px(mContext,450));
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
       // this.setClippingEnabled(false);
        this.setAnimationStyle(R.style.AppTheme);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        mView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        hiddenPopupWindow();
                    }
                }
                return true;
            }
        });
        mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void hiddenPopupWindow(){
        mView.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mView.startAnimation(animation);
        setAnimation(180,0);

    }

    private void setAnimation(int start, int end) {
        mLlContent.clearAnimation();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);

        valueAnimator.setDuration(300).start();
        valueAnimator.setTarget(mLlContent);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getBackground().setAlpha((Integer) animation.getAnimatedValue());
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        mView.clearAnimation();
        mView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in));
        setAnimation(0, 180);
        super.showAtLocation(parent, gravity, x, y);
    }

}















