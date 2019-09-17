package widget;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andlei.baseapp.R;


/**
 * 商品管理页面-选则弹窗
 * Created by soubike on 2018/7/23.
 */

public class GoodsScreenPopupWindow extends PopupWindow {
    private Context mContext;
    private final View mView;
    private LinearLayout mLlContent;
    private final TextView mTvALL;
    private final TextView mTvUp;
    private final TextView mTvDown;

    public GoodsScreenPopupWindow(Context context, View.OnClickListener onClickListener) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popupwindow_goods_screen,null);
        mTvALL = mView.findViewById(R.id.tv_all);
        mTvUp = mView.findViewById(R.id.tv_up);
        mTvDown = mView.findViewById(R.id.tv_down);

        mTvALL.setOnClickListener(onClickListener);
        mTvUp.setOnClickListener(onClickListener);
        mTvDown.setOnClickListener(onClickListener);
        initView(mView);
    }

    private void initView(View view) {
        mLlContent = view.findViewById(R.id.pop_layout);
        this.setContentView(mView);
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
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















