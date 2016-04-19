package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户详情页数据
 * Created by jianlu on 4/19/2016.
 */
public class UserInfo implements Serializable{

    private String userName;

    private String gender;

    private float bonus;

    private Integer orderNum;

    private float aveScore;

    private List<OrderComment> orderCommentList;

    public UserInfo(String userName, String gender, float bonus, Integer orderNum, float aveScore, List<OrderComment> orderCommentList) {
        this.userName = userName;
        this.gender = gender;
        this.bonus = bonus;
        this.orderNum = orderNum;
        this.aveScore = aveScore;
        this.orderCommentList = orderCommentList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public float getAveScore() {
        return aveScore;
    }

    public void setAveScore(float aveScore) {
        this.aveScore = aveScore;
    }

    public List<OrderComment> getOrderCommentList() {
        return orderCommentList;
    }

    public void setOrderCommentList(List<OrderComment> orderCommentList) {
        this.orderCommentList = orderCommentList;
    }
}
