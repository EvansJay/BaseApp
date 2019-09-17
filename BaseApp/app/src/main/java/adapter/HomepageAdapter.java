package adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.andlei.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.HomePageBean;

/**
 * @author Andlei
 * @date 2019/6/24.
 */
public class HomepageAdapter extends BaseQuickAdapter<HomePageBean, BaseViewHolder> {
    public HomepageAdapter(int layoutResId, @Nullable List<HomePageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageBean item) {
        helper.setText(R.id.tv_homepage_conetnt,item.title);
        ImageView imageView = helper.getView(R.id.img_);
        imageView.setImageResource(item.imgid);
//        switch (helper.getLayoutPosition()){
//            case 0:
//                imageView.setImageResource(R.mipmap.message);
//                break;
//            case 1:
//                imageView.setImageResource(R.mipmap.php);
//                break;
//            case 2:
//                imageView.setImageResource(R.mipmap.attachment);
//                break;
//            case 3:
//                imageView.setImageResource(R.mipmap.table);
//                break;
//            case 4:
//                imageView.setImageResource(R.mipmap.zip);
//                break;
//            case 5:
//                imageView.setImageResource(R.mipmap.shredder);
//                break;
//            default:
//                imageView.setImageResource(R.color.write);
//                break;
//        }
    }
}
