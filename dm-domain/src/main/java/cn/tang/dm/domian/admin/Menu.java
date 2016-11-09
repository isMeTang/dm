package cn.tang.dm.domian.admin;

import cn.tang.dm.domian.common.BaseDomain;

import java.util.Date;

/**
 * 菜单实体
 *
 * @author tangzeqian
 * @create 2016-03-05  9:50
 */
public class Menu extends BaseDomain {
    /**
     * 菜单名
     */
    private String name;
    /**
     * 菜单链接地址
     */
    private String url;
    /**
     * 父菜单id
     */
    private long parentId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
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
        return "Menu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parentId=" + parentId +
                ", state=" + state +
                ", date=" + date +
                "} " + super.toString();
    }
}
