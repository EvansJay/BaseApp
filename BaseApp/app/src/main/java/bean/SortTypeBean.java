package bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import base.BaseBean;

/**
 * @author Andlei
 * @date 2019/8/29.
 */

@SuppressLint("ParcelCreator")
public class SortTypeBean extends BaseBean implements Parcelable {
    public SortTypeBean() {

    }
    public SortTypeBean(int enable, int type, String start_time, String end_time) {
        this.enable = enable;
        this.type = type;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    /**
     * enable : 0
     * type : 1
     * start_time : 0
     * end_time : 0
     */

    private int enable;
    private int type;
    private String start_time;
    private String end_time;

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(enable);
        parcel.writeInt(type);
        parcel.writeString(start_time);
        parcel.writeString(end_time);
    }

    public static final Parcelable.Creator<SortTypeBean> CREATOR  = new Creator<SortTypeBean>() {
        //实现从source中创建出类的实例的功能
        @Override
        public SortTypeBean createFromParcel(Parcel source) {
            SortTypeBean parInfo  = new SortTypeBean();
            parInfo.enable = source.readInt();
            parInfo.type = source.readInt();
            parInfo.start_time = source.readString();
            parInfo.end_time = source.readString();
            return parInfo;
        }
        //创建一个类型为T，长度为size的数组
        @Override
        public SortTypeBean[] newArray(int size) {
            return new SortTypeBean[size];
        }
    };
}
