package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/4/17.
 */
public class ChangeIndexEvent {

    private Integer index;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ChangeIndexEvent(Integer index, Integer status) {
        this.index = index;
        this.status = status;
    }
}
