package event;


import java.util.List;

import bean.GoodsDetailsEntity;

/**
 * Created by soubike on 2018/8/13.
 */

public class SpecEvent {
    private List<GoodsDetailsEntity.GoodsDetailBean.SpenBean> specBeanList;


    public List<GoodsDetailsEntity.GoodsDetailBean.SpenBean> getSpecBeanList() {
        return specBeanList;
    }

    public void setSpecBeanList(List<GoodsDetailsEntity.GoodsDetailBean.SpenBean> specBeanList) {
        this.specBeanList = specBeanList;
    }

}
