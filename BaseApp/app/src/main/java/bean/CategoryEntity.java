package bean;

import java.util.List;

import base.BaseBean;


/**
 * Created by soubike on 2018/7/31.
 */

public class CategoryEntity extends BaseBean {

    private List<CategoryListBean> category_list;

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
    }

    public static class CategoryListBean {
        /**
         * id : 749
         * store_id : 9
         * name : 赫赫
         * sort : 1
         * add_time : 2018-07-07 22:22:27
         */

        private String id;
        private String store_id;
        private String name;
        private String sort;
        private String add_time;
        private boolean viewDrag;
        public boolean isSelect;

        public boolean isViewDrag() {
            return viewDrag;
        }

        public void setViewDrag(boolean viewDrag) {
            this.viewDrag = viewDrag;
        }




        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
