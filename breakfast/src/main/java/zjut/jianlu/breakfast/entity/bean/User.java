package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/3/9.
 */
public class User  implements Serializable {

    private Long id;//id

    private String mobile;//手机号

    private String password;//密码

    private String username;//用户名

    private Integer type;//用户类型 0-需方 1-供方

    private Integer gender;//性别 0-女生，1-男生

    private String brief;//个人简介

    private String address;//收货地址

    private Float bonus;//悬赏金

    private Integer orderNum;// 订单数

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public User() {
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", mobile='" + mobile + '\'' +
//                ", password='" + password + '\'' +
//                ", username='" + username + '\'' +
//                ", type=" + type +
//                ", gender=" + gender +
//                ", brief='" + brief + '\'' +
//                ", address='" + address + '\'' +
//                ", bonus=" + bonus +
//                ", orderNum=" + orderNum +
//                '}';
//    }
}
