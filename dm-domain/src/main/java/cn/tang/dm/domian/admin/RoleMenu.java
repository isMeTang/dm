package cn.tang.dm.domian.admin;

import cn.tang.dm.domian.common.BaseDomain;

import java.util.Date;

/**
 * 角色菜单实体
 *
 * @author tangzeqian
 * @create 2016-03-05  9:53
 */
public class RoleMenu extends BaseDomain {
    /**
     * 角色id
     */
    private long roleId;
    /**
     * 菜单id
     */
    private long menuId;
    /**
     * 创建时间
     */
    private Date date;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                "roleId=" + roleId +
                ", menuId=" + menuId +
                ", date=" + date +
                "} " + super.toString();
    }
}
