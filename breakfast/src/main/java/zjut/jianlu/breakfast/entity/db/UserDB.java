package zjut.jianlu.breakfast.entity.db;

import com.orm.SugarRecord;

import java.util.List;

import zjut.jianlu.breakfast.entity.bean.User;

/**
 * Created by jianlu on 16/3/9.
 * 用户本地数据库表
 */

public class UserDB extends SugarRecord {

    private Long userId;

    private String mobile;

    private String password;

    private String username;

    private Integer type;//用户类型 0-需方 1-供方

    private Integer gender;//性别 0-女生，1-男生

    private String brief;//个人简介

    private String address;//收货地址

    private float bonus;

    private Integer orderNum;

    public UserDB() {

    }

    public UserDB(User user) {
        userId = user.getId();
        mobile = user.getMobile();
        password = user.getPassword();
        username = user.getUsername();
        type = user.getType();
        gender = user.getGender();
        address = user.getAddress();
        brief = user.getBrief();
        bonus = user.getBonus();
        orderNum = user.getOrderNum();

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    List<OrderInfoDB> getClientOrderInfos() {
        return OrderInfoDB.find(OrderInfoDB.class, "clientUser = ?", new String[]{String.valueOf(userId)});
    }

    List<OrderInfoDB> getCourierOrderInfos() {
        return OrderInfoDB.find(OrderInfoDB.class, "courierUser = ?", new String[]{String.valueOf(userId)});
    }
}
