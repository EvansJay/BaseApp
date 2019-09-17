package event;


import java.util.ArrayList;

import bean.GoodsDetailsEntity;

/**
 * Created by soubike on 2018/8/13.
 */

public class AttrEvent {
    public AttrEvent(ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> attrBeanList) {
        this.attrBeanList = attrBeanList;
    }

    public ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> getAttrBeanList() {
        return attrBeanList;
    }

    public void setAttrBeanList(ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> attrBeanList) {
        this.attrBeanList = attrBeanList;
    }

    private ArrayList<GoodsDetailsEntity.GoodsDetailBean.AttrBean> attrBeanList;
}
