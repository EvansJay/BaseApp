package bean;
import java.io.Serializable;
import java.util.ArrayList;

import base.BaseBean;


/**
 * Created by soubike on 2018/8/3.
 */

public class GoodsDetailsEntity extends BaseBean implements Serializable {

    /**
     * goods_detail : {"goods_id":"9222","name":"商品5","icon":"http://img1.birdback.org/a200/d8/b3/d8b325676cec4b9f6b119a51963980bf.jpg","img_path":"a/70/bb/70bb30a3583f0817481924319e784059.jpg,1600,1600","num":9999,"buy_num":"0","status":1,"price":17,"description":"商品5描述","attr":[{"id":"269","name":"辣度","item":"不辣|微辣"}]}
     */

    private GoodsDetailBean goods_detail = new GoodsDetailBean();

    public GoodsDetailBean getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(GoodsDetailBean goods_detail) {
        this.goods_detail = goods_detail;
    }

    public static class GoodsDetailBean implements Serializable {
        /**
         * goods_id : 9222
         * name : 商品5
         * icon : http://img1.birdback.org/a200/d8/b3/d8b325676cec4b9f6b119a51963980bf.jpg
         * img_path : a/70/bb/70bb30a3583f0817481924319e784059.jpg,1600,1600
         * num : 9999
         * buy_num : 0
         * status : 1
         * price : 17
         * description : 商品5描述
         * attr : [{"id":"269","name":"辣度","item":"不辣|微辣"}]
         */

        private String goods_id = "";
        private String name = "";
        private String icon = "";
        private String img_path = "";
        private String num = "10000";
        private String buy_num = "";
        private int status;
        private String price = "";
        private String description = "";
        private String category_id = "";
        private String category_name = "";
        private String food_box_price = "";
        private String box_id = "";
        private ArrayList<SpenBean> spec = new ArrayList<>();
        private ArrayList<AttrBean> attr = new ArrayList<>();

        public ArrayList<SpenBean> getSpec() {
            return spec;
        }

        public void setSpec(ArrayList<SpenBean> spec) {
            this.spec = spec;
        }


        public String getBox_id() {
            return box_id;
        }

        public void setBox_id(String box_id) {
            this.box_id = box_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }


        public String getFood_box_price() {
            return food_box_price;
        }

        public void setFood_box_price(String food_box_price) {
            this.food_box_price = food_box_price;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }


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

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getNum() {
            return num == null ? "" :num;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(ArrayList<AttrBean> attr) {
            this.attr = attr;
        }

        public static class AttrBean implements Serializable {
            /**
             * id : 269
             * name : 辣度
             * item : 不辣|微辣
             */

            private String id = "0";
            private String name = "";
            private String item = "";

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }

        public static class SpenBean implements Serializable {

            /**
             * id : 1949
             * name : 韩式炸鸡（小份）+波霸奶茶（中杯）
             * price : 22.95
             */

            private String id = "0";
            private String name = "";
            private String price = "";

            private String food_box_price = ""; //餐盒费
            private String num = "";  //库存
            private String num_max = "10000";  //最大库存
            private String is_auto_fill = "0";  //次日自动置满

            public String getFood_box_price() {
                return food_box_price;
            }

            public void setFood_box_price(String food_box_price) {
                this.food_box_price = food_box_price;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getNum_max() {
                return num_max;
            }

            public void setNum_max(String num_max) {
                this.num_max = num_max;
            }

            public String isIs_auto_fill() {
                return is_auto_fill;
            }

            public void setIs_auto_fill(String is_auto_fill) {
                this.is_auto_fill = is_auto_fill;
            }



            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }


}
