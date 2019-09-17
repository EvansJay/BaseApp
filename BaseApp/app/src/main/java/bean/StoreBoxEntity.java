package bean;

import base.BaseBean;

/**
 * @author Andlei
 * @date 2019/6/4.
 */
public class StoreBoxEntity extends BaseBean {

    /**
     * box_id : 15
     * type : 1
     * money : 0.01
     */

    private String box_id;
    private String type;
    private String money;

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
