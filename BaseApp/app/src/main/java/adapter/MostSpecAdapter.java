package adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.andlei.baseapp.R;

import java.util.ArrayList;

import application.App;
import bean.GoodsDetailsEntity;
import ui.activity.goods.MostSpecActivity;
import ui.activity.login.LoginActivity;
import utils.SPUtils;
import widget.HiDialog;

public class MostSpecAdapter extends RecyclerView.Adapter<MostSpecAdapter.ViewHolder>  {
    private ArrayList<GoodsDetailsEntity.GoodsDetailBean.SpenBean>  mData;
    private MostSpecActivity mostSpecActivity;
    public MostSpecAdapter(MostSpecActivity mostSpecActivity,ArrayList<GoodsDetailsEntity.GoodsDetailBean.SpenBean> data) {
        this.mData = data;
        this.mostSpecActivity = mostSpecActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_spec, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);//不复用布局
        holder.et_spec_name.setText(mData.get(position).getName());
        holder.et_price.setText(mData.get(position).getPrice());
        holder.et_num.setText(mData.get(position).getNum());


        holder.et_spec_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setName(holder.et_spec_name.getText().toString());
            }
        });
        holder.et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setPrice(holder.et_price.getText().toString());
            }
        });
        holder.et_box_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setFood_box_price(holder.et_box_price.getText().toString());
            }
        });
        holder.et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setNum(holder.et_num.getText().toString());//todo
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.tv_num_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.et_num.setText("0");
                mData.get(position).setNum("0");
            }
        });

        holder.tv_num_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.et_num.setText("10000");
                mData.get(position).setNum("10000");
            }
        });

        holder.switch_tab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    new HiDialog.Builder(mostSpecActivity)
                            .setContent("开启后，次日00:00自动置满库存")
                            .setRightBtnText("知道了")
                            .setRightCallBack(new HiDialog.RightClickCallBack() {
                                @Override
                                public void dialogRightBtnClick() {

                                }
                            }).build();
                }
                mData.get(position).setIs_auto_fill(isChecked?"1":"0");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_num_clean,tv_num_max,tv_delete;
        private final EditText et_spec_name,et_price,et_box_price,et_num;
        private Switch switch_tab;

        public ViewHolder(View itemView) {
            super(itemView);
            et_spec_name = itemView.findViewById(R.id.et_spec_name);
            et_price = itemView.findViewById(R.id.et_price);
            et_box_price = itemView.findViewById(R.id.et_box_price);
            et_num = itemView.findViewById(R.id.et_num);
            tv_num_clean = itemView.findViewById(R.id.tv_num_clean);
            tv_num_max = itemView.findViewById(R.id.tv_num_max);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            switch_tab = itemView.findViewById(R.id.switch_tab);

        }
    }

    public OnRecyclerViewListener mOnRecyclerViewListener;
    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        this.mOnRecyclerViewListener = l;
    }
}
