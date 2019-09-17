package bean;

import android.support.annotation.NonNull;

import java.util.List;

import base.BaseBean;

/**
 * Created by soubike on 2018/8/2.
 */

public class GoodsRightEntity extends BaseBean {

    private List<GoodsListBean> goods_list;

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean implements  Comparable<GoodsListBean>{
        /**
         * goods_id : 9222
         * name : 商品5
         * icon : http://img1.birdback.org/a200/d8/b3/d8b325676cec4b9f6b119a51963980bf.jpg
         * num : 9999
         * buy_num : 0
         * status : 1
         * price : 17
         */

        private String goods_id;
        private String name;
        private String icon;
        private String num;
        private String buy_num = "0";
        private int status;
        private String price;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getBuy_num() {
            return buy_num;
        }

        public void setBuy_num(String buy_num) {
            this.buy_num = buy_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public int compareTo(@NonNull GoodsListBean o) {

            return Integer.parseInt(o.buy_num) - Integer.parseInt(this.buy_num);
        }
    }
}
