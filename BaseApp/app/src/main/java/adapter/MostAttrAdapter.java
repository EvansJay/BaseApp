package adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.andlei.baseapp.R;

import java.util.ArrayList;
import java.util.List;

import bean.GoodsDetailsEntity;
import widget.EditTagsView;

public class MostAttrAdapter extends RecyclerView.Adapter<MostAttrAdapter.ViewHolder> {

    private List<GoodsDetailsEntity.GoodsDetailBean.AttrBean> mData;

    public MostAttrAdapter(ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_attr, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);//不复用布局

        final GoodsDetailsEntity.GoodsDetailBean.AttrBean attrBean = mData.get(position);
        holder.et_attr_name.setText(attrBean.getName());
        holder.et_attr_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setName(s.toString());
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });

        final String item = attrBean.getItem();
        String[] split = item.split("\\|");

        for (int i = 0; i < split.length; i++) {

            holder.edTags.addTag(split[i]);
        }

        holder.edTags.setTagListener(new EditTagsView.onTagAddListener() {
            @Override
            public void onTagAdd(String tag) {

                if (mData.get(position).getItem().toString().trim().length() <= 0){
                    mData.get(position).setItem(mData.get(position).getItem().concat(tag));
                }else {
                    mData.get(position).setItem(mData.get(position).getItem().concat("|").concat(tag));
                }

            }

            @Override
            public void onTagError(Type errorType) {
                if (errorType == Type.AGAIN) {
                    utils.TextUtils.makeText("属性重复");
                }
                if (errorType == Type.COUNT_MAX){
                    utils.TextUtils.makeText("数量超限");
                }
            }

            @Override
            public void onTagDelete(String tag) {
                if (!mData.get(position).getItem().toString().contains("|")){
                    mData.get(position).setItem("");
                }else {
                    mData.get(position).setItem(mData.get(position).getItem().replace("|"+tag,""));
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_delete;
        private final EditText et_attr_name;
        private final EditTagsView edTags;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            et_attr_name = itemView.findViewById(R.id.et_attr_name);
            edTags = itemView.findViewById(R.id.edtTags);


        }
    }

    public OnRecyclerViewListener mOnRecyclerViewListener;
    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        this.mOnRecyclerViewListener = l;
    }
}
