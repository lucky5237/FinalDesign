package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 4/18/2016.
 */
public class OrderComment implements Serializable {

    private Integer id;

    private Integer orderId;

    private float clientScore;

    private String clinetComment;

    private float courierScore;

    private String courierComment;

    private String clientCommentTs;

    private String courierCommentTs;

    private Integer clientUserId;

    private Integer courierUserId;

    public OrderComment(Integer id, Integer orderId, float clientScore, String clinetComment, float courierScore,
            String courierComment, String clientCommentTs, String courierCommentTs, Integer clientUserId,
            Integer courierUserId) {
        this.id = id;
        this.orderId = orderId;
        this.clientScore = clientScore;
        this.clinetComment = clinetComment;
        this.courierScore = courierScore;
        this.courierComment = courierComment;
        this.clientCommentTs = clientCommentTs;
        this.courierCommentTs = courierCommentTs;
        this.clientUserId = clientUserId;
        this.courierUserId = courierUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public float getClientScore() {
        return clientScore;
    }

    public void setClientScore(float clientScore) {
        this.clientScore = clientScore;
    }

    public String getClinetComment() {
        return clinetComment;
    }

    public void setClinetComment(String clinetComment) {
        this.clinetComment = clinetComment;
    }

    public float getCourierScore() {
        return courierScore;
    }

    public void setCourierScore(float courierScore) {
        this.courierScore = courierScore;
    }

    public String getCourierComment() {
        return courierComment;
    }

    public void setCourierComment(String courierComment) {
        this.courierComment = courierComment;
    }

    public String getClientCommentTs() {
        return clientCommentTs;
    }

    public void setClientCommentTs(String clientCommentTs) {
        this.clientCommentTs = clientCommentTs;
    }

    public String getCourierCommentTs() {
        return courierCommentTs;
    }

    public void setCourierCommentTs(String courierCommentTs) {
        this.courierCommentTs = courierCommentTs;
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Integer getCourierUserId() {
        return courierUserId;
    }

    public void setCourierUserId(Integer courierUserId) {
        this.courierUserId = courierUserId;
    }
}
