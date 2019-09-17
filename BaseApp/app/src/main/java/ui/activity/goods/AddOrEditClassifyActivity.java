package ui.activity.goods;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andlei.baseapp.R;

import base.activity.BaseLayoutActivity;

/**
 * 新增，编辑分类
 * @author Andlei
 * @date 2019/9/15.
 */
public class AddOrEditClassifyActivity extends BaseLayoutActivity {
    private EditText et_goods_name;
    private Button btn_add,btn_delete;
    private String class_id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_addoredit_classify;
    }

    @Override
    protected void findViews() {
        et_goods_name = getView(R.id.et_goods_name);
        btn_add = getView(R.id.btn_add);
        btn_delete = getView(R.id.btn_delete);
    }

    @Override
    protected void formatViews() {
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    protected void formatData() {
        if(!TextUtils.isEmpty(class_id)){
            setTitle("编辑分类",true);
            btn_delete.setVisibility(View.VISIBLE);
        }else {
            setTitle("添加分类",true);
            btn_delete.setVisibility(View.GONE);
        }

    }

    @Override
    protected void getBundle(Bundle bundle) {
        if(bundle != null){
            class_id = bundle.getString("class_id");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                // TODO 保存 或 添加 种类

                break;
            case R.id.btn_delete:
                // TODO  删除该类型

                break;
        }
    }
}
