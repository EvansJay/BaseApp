package bean;

import base.BaseBean;

/**
 * @author Andlei
 * @date 2019/6/28.
 */
public class SortPlanBean extends BaseBean {

    /**
     * id : 412
     * plan_name : null
     * school_id : 16
     * store_id : 9
     * plan_type : 0
     * sort : 3
     *  category : null
     */

    private String id;
 //   private Object plan_name;
    private String school_id;
    private String store_id;
    private String plan_type;
    private String sort;
  //  private Object category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Object getPlan_name() {
//        return plan_name;
//    }
//
//    public void setPlan_name(Object plan_name) {
//        this.plan_name = plan_name;
//    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
