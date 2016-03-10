package zjut.jianlu.breakfast.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by jianlu on 16/3/9.
 */
public class User extends BmobUser {

    private Integer type;//用户类型 0-需方 1-供方

    private Integer gender;//性别 0-女生，1-男生

    private String brief;//个人简介

    private String address;//收货地址

    public User(){
        setTableName("_user");
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


    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", gender=" + gender +
                ", brief='" + brief + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
