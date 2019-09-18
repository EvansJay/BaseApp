package bean;


import base.BaseBean;

public class SchoolBean extends BaseBean {

    /**
     * id : 15
     * name : 上海政法学院
     * is_vest : 0
     * city_id : 1
     * prov_id : 1
     * long_lat : 121.17327,31.11047
     * geohash : wtw0vr7xtgf
     * status : 1
     * sort : 0
     * add_time : 2018-04-05 13:50:08
     */

    private String id;
    private String name;
    private String is_vest;
    private String city_id;
    private String prov_id;
    private String long_lat;
    private String geohash;
    private String status;
    private String sort;
    private String add_time;

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

    public String getIs_vest() {
        return is_vest;
    }

    public void setIs_vest(String is_vest) {
        this.is_vest = is_vest;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getProv_id() {
        return prov_id;
    }

    public void setProv_id(String prov_id) {
        this.prov_id = prov_id;
    }

    public String getLong_lat() {
        return long_lat;
    }

    public void setLong_lat(String long_lat) {
        this.long_lat = long_lat;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
