package cn.tang.dm.domian.admin;

import cn.tang.dm.domian.common.BaseDomain;

import java.util.Date;

/**
 * 角色实体
 *
 * @author tangzeqian
 * @create 2016-03-05  9:36
 */
public class Role extends BaseDomain {
    /**
     * 角色名
     */
    private String name;
    /**
     * 状态
     */
    private int state;
    /**
     * 创建时间
     */
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", date=" + date +
                "} " + super.toString();
    }
}
