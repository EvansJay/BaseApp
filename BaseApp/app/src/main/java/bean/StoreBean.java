package bean;


import base.BaseBean;

public class StoreBean extends BaseBean {

    /**
     * id : 732
     * shop_name : 坛老头酸菜鱼
     */

    private String id;
    private String shop_name;
    private String desc;

    private int disable;
    public boolean isSelect;
    private int bind_id;
    public int sort;
    public int getBind_id() {
        return bind_id;
    }

    public void setBind_id(int bind_id) {
        this.bind_id = bind_id;
    }

    /**
     * is_vest : 0
     * menu_id : 7
     * icon_tag : 1
     * open_id : oOngy0f-5gwMMNheCGsPScQPQ01w
     * wallet : 8145
     * total_wallet : 8145
     * history_cash : 0
     * img_path : a/67/8f/678f77c78ab52b3951b4c9d4c3e802f6.png,634,475
     * share_img_path :
     * star : 0
     * start_send_price : 20
     * send_price : 2
     * send_subsidy : 4.00
     * order_extract : 0.050
     * address :
     * long_lat :
     * mobile : 17321013922
     * account : 17321013922
     * password : 123456
     * tags : {＂discount＂:[[＂20＂,＂5＂],[＂25＂,＂7＂],[＂50＂,＂10＂],[＂100＂,＂20＂]]}
     * history_order : 47
     * month_order : 6
     * status : 1
     * is_open : 1
     * sort : 13
     * notice : 上午10:00开始配送，上政学生配送到楼。
     * disable_time : 10:00-21:00
     * disable_sort : 0
     * use_feie : 1
     * add_time : 2018-06-26 11:54:19
     * distribution_type : 0
     * goods_discount : 0
     * open_group_active : 0
     * open_oneself : 0
     * is_finish_authority : 0
     * disable_sort_v2 : 1
     * is_pack : 0
     * invite_rat : 0.006
     * package_money : 0.00
     * invite_start_send_price : 0.00
     * reserve_today : 0
     * shop_img : http://img1.birdback.org/a200/df/03/df03eb47577d44abad5c94f29c9a3fc8.png
     * icon_tag_url : http://img1.birdback.org/daoloucion@2x.png
     */

    private String is_vest;
    private String menu_id;
    private String icon_tag;
    private String open_id;
    private String wallet;
    private String total_wallet;
    private String history_cash;
    private String img_path;
    private String share_img_path;
    private String star;
    private String start_send_price;
    private String send_price;
    private String send_subsidy;
    private String order_extract;
    private String address;
    private String long_lat;
    private String mobile;
    private String account;
    private String password;
    private String tags;
    private String history_order;
    private String month_order;
    private String status;
    private int is_open;
//    private int sort;
    private String notice;
    private String disable_time;
    private String disable_sort;
    private String use_feie;
    private String add_time;
    private String distribution_type;
    private String goods_discount;
    private String open_group_active;
    private String open_oneself;
    private String is_finish_authority;
    private String disable_sort_v2;
    private String is_pack;
    private String invite_rat;
    private String package_money;
    private String invite_start_send_price;
    private String reserve_today;
    private String shop_img;
    private String icon_tag_url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_name() { return shop_name; }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getIs_vest() {
        return is_vest;
    }

    public void setIs_vest(String is_vest) {
        this.is_vest = is_vest;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getIcon_tag() {
        return icon_tag;
    }

    public void setIcon_tag(String icon_tag) {
        this.icon_tag = icon_tag;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getTotal_wallet() {
        return total_wallet;
    }

    public void setTotal_wallet(String total_wallet) {
        this.total_wallet = total_wallet;
    }

    public String getHistory_cash() {
        return history_cash;
    }

    public void setHistory_cash(String history_cash) {
        this.history_cash = history_cash;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getShare_img_path() {
        return share_img_path;
    }

    public void setShare_img_path(String share_img_path) {
        this.share_img_path = share_img_path;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStart_send_price() {
        return start_send_price;
    }

    public void setStart_send_price(String start_send_price) {
        this.start_send_price = start_send_price;
    }

    public String getSend_price() {
        return send_price;
    }

    public void setSend_price(String send_price) {
        this.send_price = send_price;
    }

    public String getSend_subsidy() {
        return send_subsidy;
    }

    public void setSend_subsidy(String send_subsidy) {
        this.send_subsidy = send_subsidy;
    }

    public String getOrder_extract() {
        return order_extract;
    }

    public void setOrder_extract(String order_extract) {
        this.order_extract = order_extract;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLong_lat() {
        return long_lat;
    }

    public void setLong_lat(String long_lat) {
        this.long_lat = long_lat;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getHistory_order() {
        return history_order;
    }

    public void setHistory_order(String history_order) {
        this.history_order = history_order;
    }

    public String getMonth_order() {
        return month_order;
    }

    public void setMonth_order(String month_order) {
        this.month_order = month_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDisable_time() {
        return disable_time;
    }

    public void setDisable_time(String disable_time) {
        this.disable_time = disable_time;
    }

    public String getDisable_sort() {
        return disable_sort;
    }

    public void setDisable_sort(String disable_sort) {
        this.disable_sort = disable_sort;
    }

    public String getUse_feie() {
        return use_feie;
    }

    public void setUse_feie(String use_feie) {
        this.use_feie = use_feie;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDistribution_type() {
        return distribution_type;
    }

    public void setDistribution_type(String distribution_type) {
        this.distribution_type = distribution_type;
    }

    public String getGoods_discount() {
        return goods_discount;
    }

    public void setGoods_discount(String goods_discount) {
        this.goods_discount = goods_discount;
    }

    public String getOpen_group_active() {
        return open_group_active;
    }

    public void setOpen_group_active(String open_group_active) {
        this.open_group_active = open_group_active;
    }

    public String getOpen_oneself() {
        return open_oneself;
    }

    public void setOpen_oneself(String open_oneself) {
        this.open_oneself = open_oneself;
    }

    public String getIs_finish_authority() {
        return is_finish_authority;
    }

    public void setIs_finish_authority(String is_finish_authority) {
        this.is_finish_authority = is_finish_authority;
    }

    public String getDisable_sort_v2() {
        return disable_sort_v2;
    }

    public void setDisable_sort_v2(String disable_sort_v2) {
        this.disable_sort_v2 = disable_sort_v2;
    }

    public String getIs_pack() {
        return is_pack;
    }

    public void setIs_pack(String is_pack) {
        this.is_pack = is_pack;
    }

    public String getInvite_rat() {
        return invite_rat;
    }

    public void setInvite_rat(String invite_rat) {
        this.invite_rat = invite_rat;
    }

    public String getPackage_money() {
        return package_money;
    }

    public void setPackage_money(String package_money) {
        this.package_money = package_money;
    }

    public String getInvite_start_send_price() {
        return invite_start_send_price;
    }

    public void setInvite_start_send_price(String invite_start_send_price) {
        this.invite_start_send_price = invite_start_send_price;
    }

    public String getReserve_today() {
        return reserve_today;
    }

    public void setReserve_today(String reserve_today) {
        this.reserve_today = reserve_today;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public String getIcon_tag_url() {
        return icon_tag_url;
    }

    public void setIcon_tag_url(String icon_tag_url) {
        this.icon_tag_url = icon_tag_url;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StoreBean) {
            StoreBean u = (StoreBean) obj;
            return this.id.equals(u.id)
                    && this.shop_name.equals(shop_name);
        }

        return super.equals(obj);
    }
}
