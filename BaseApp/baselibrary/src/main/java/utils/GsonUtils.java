package utils;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import base.BaseBean;


/**
 * Created by xulei on 2017/11/30.
 */

public class GsonUtils<T> implements Serializable {
    private static GsonUtils utils;

    public GsonUtils(Context context) {
    }

    public static GsonUtils getInstance(Context context) {
        if (utils == null) {
            utils = new GsonUtils(context);
        }
        return utils;
    }
    //将bean转换成Json字符串
    public static String beanToJSONString(Object bean) {
        return new Gson().toJson(bean);
    }
    //将Json字符串转换成对象：
    public static Object JSONToObject(String json, Class beanClass) {
        Gson gson = new Gson();
        Object res = gson.fromJson(json, beanClass);
        return res;
    }

    /**
     * 解析没有数据头的纯数组
     */
    public <T> List<T> parseNoHeaderJArray(BaseBean baseBean, Class<T> classOfT) {
        //拿到本地JSON 并转成String
        //String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_1);
        //Json的解析类对象
        String myJson=  new Gson().toJson(baseBean.getData());//将gson转化为json
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray;
        try {
            jsonArray = parser.parse(myJson).getAsJsonArray();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        List<T> BeanList = new ArrayList<>();
        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            T Bean =  gson.fromJson(user, classOfT);
            BeanList.add(Bean);
        }
       return  BeanList;
    }
    /**
     * 解析有数据头的纯数组
     */
    public <T> List<T> parseHaveHeaderJArray(BaseBean baseBean, Class<T> classOfT, String list) {
        //拿到本地JSON 并转成String
        String myJson=  new Gson().toJson(baseBean.getData());//将gson转化为json
        JsonParser parser = new JsonParser();

        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(myJson).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray(list);
        if(jsonArray == null){
            return  new ArrayList<>();
        }
        Gson gson = new Gson();
        ArrayList<T> userBeanList = new ArrayList<>();
        //循环遍历
        for (JsonElement user : jsonArray) {
            //通过反射 得到UserBean.class
            T userBean = gson.fromJson(user, classOfT);
            userBeanList.add(userBean);
        }
        return  userBeanList;
    }
    /**
     * 有消息头 复杂数据 常规方式
     */
    public T parseComplexJArrayByCommon(BaseBean baseBean, Class<T> classOfT) {
        //拿到Json字符串
        //Json的解析类对象
        String myJson=  new Gson().toJson(baseBean.getData());//将gson转化为json
        //GSON直接解析成对象
        T resultBean = new Gson().fromJson(myJson,classOfT);
        return resultBean;
    }
}
