package bean;

import java.util.List;

import base.BaseBean;

/**
 * @author Andlei
 * @date 2019/8/28.
 */
public class SchoolDistributionBean extends BaseBean {


    /**
     * money : 11
     * in_reward : 22
     * in_sub : 1
     * in_deadline : 1
     * out_money : 1
     * out_reward : 1
     * out_sub : 1
     * out_deadline : 1
     * buildingList : [{"id":"389","name":"浦江科技广场"},{"id":"416","name":"21号楼"},{"id":"415","name":"20号楼"},{"id":"414","name":"18号楼"},{"id":"413","name":"17号楼"},{"id":"412","name":"16号楼"},{"id":"398","name":"9号楼"},{"id":"397","name":"8号楼"},{"id":"396","name":"7号楼"},{"id":"394","name":"5号楼"},{"id":"392","name":"3号楼"},{"id":"391","name":"2号楼"},{"id":"390","name":"1号楼"},{"id":"417","name":"其他"}]
     */

    private String money;
    private String in_reward;
    private String in_sub;
    private int in_deadline;
    private String out_money;
    private String out_reward;
    private String out_sub;
    private int out_deadline;
    private List<BuildingListBean> buildingList;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIn_reward() {
        return in_reward;
    }

    public void setIn_reward(String in_reward) {
        this.in_reward = in_reward;
    }

    public String getIn_sub() {
        return in_sub;
    }

    public void setIn_sub(String in_sub) {
        this.in_sub = in_sub;
    }

    public int getIn_deadline() {
        return in_deadline;
    }

    public void setIn_deadline(int in_deadline) {
        this.in_deadline = in_deadline;
    }

    public String getOut_money() {
        return out_money;
    }

    public void setOut_money(String out_money) {
        this.out_money = out_money;
    }

    public String getOut_reward() {
        return out_reward;
    }

    public void setOut_reward(String out_reward) {
        this.out_reward = out_reward;
    }

    public String getOut_sub() {
        return out_sub;
    }

    public void setOut_sub(String out_sub) {
        this.out_sub = out_sub;
    }

    public int getOut_deadline() {
        return out_deadline;
    }

    public void setOut_deadline(int out_deadline) {
        this.out_deadline = out_deadline;
    }

    public List<BuildingListBean> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<BuildingListBean> buildingList) {
        this.buildingList = buildingList;
    }

    public static class BuildingListBean {
        /**
         * id : 389
         * name : 浦江科技广场
         */

        private String b_id ="";
        private String name = "";
        public String b_money = "";

        public String getB_id() {
            return b_id;
        }

        public void setB_id(String b_id) {
            this.b_id = b_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
