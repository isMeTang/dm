package cn.tang.dm.domian.common;

/**
 * 基本实体
 *
 * @author tangzeqian
 * @create 2016-02-25  21:50
 */
public class BaseDomain {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BaseDomain() {
    }

    @Override
    public String toString() {
        return "BaseDomain{" +
                "id=" + id +
                '}';
    }
}
