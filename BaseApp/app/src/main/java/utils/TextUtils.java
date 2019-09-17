package utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import application.App;

/**
 * Created by Administrator on 2018/4/8.
 */

public class TextUtils {

    private static final Context mContext;
    private static Toast toast;
    static {
        mContext = App.getInstance();
    }

    public static void makeText(String text){
        try {
            showToast(text);
        }catch (Exception e){
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            showToast(text);
            Looper.loop();
        }

    }

    public static void makeText(int text){
        showToast(String.valueOf(text));
    }

    private static void showToast(String text){
        if(toast != null){
            toast.cancel();
        }

        toast=Toast.makeText(mContext,null, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**
     * 解析没有数据头的纯数组
     */
    public static  <T> List<T> parseNoHeaderJArray(Object baseBean, Class<T> classOfT) {
        //拿到本地JSON 并转成String
        //String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_1);
        //Json的解析类对象
        String myJson=  new Gson().toJson(baseBean);//将gson转化为json
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
     * 返回Json字符串
     *
     *
     *            返回结果
     * @param jsonMap
     *            需要返回的数据集
     * @return Json字符串
     */
    public static String toJson(Map<String, String> jsonMap) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        if (jsonMap.size() > 0) {

            for (String key : jsonMap.keySet()) {
                if (!key.equals("class"))
                    buffer.append(key + " : '" + jsonMap.get(key) + "',");
            }
            // 去掉最后一个','
            buffer.deleteCharAt(buffer.length() - 1);
        }
        buffer.append("}");
        return buffer.toString();
    }
    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return dateString;
    }

    // 删除ArrayList中重复元素，保持顺序
    public static List removeDuplicate(List list){
        List listTemp = new ArrayList();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
}
