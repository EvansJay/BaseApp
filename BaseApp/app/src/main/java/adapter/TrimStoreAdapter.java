package adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.StoreBean;

/**
 * @author Andlei
 * @date 2019/6/26.
 */
public class TrimStoreAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {

    public TrimStoreAdapter( int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final StoreBean item) {
        ImageView imageView = helper.getView(R.id.img_select);
        if(item.getDisable() == 1){
            helper.setText(R.id.tv_text,(helper.getAdapterPosition()+1)+"."+item.getShop_name()+"(休息中)");
        }else {
            helper.setText(R.id.tv_text,(helper.getAdapterPosition()+1)+"."+item.getShop_name()+"(营业中)");
        }
        if(item.isSelect){
            imageView.setImageResource(R.mipmap.icon_is_select);
        }else {
            imageView.setImageResource(R.mipmap.icon_no_select);
        }
        EditText edit_peisong =  helper.getView(R.id.edit_peisongfei);
        EditText edit_qisong =  helper.getView(R.id.edit_qisongfei);
        edit_peisong.setText(item.getSend_price());
        edit_qisong.setText(item.getStart_send_price());
        edit_peisong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                item.setSend_price(editable.toString());
            }
        });
        edit_qisong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                item.setStart_send_price(editable.toString());

            }
        });
        helper.addOnClickListener(R.id.img_select);
        helper.addOnClickListener(R.id.btn_sure);
        helper.setIsRecyclable(false);//禁止复用
    }
}
