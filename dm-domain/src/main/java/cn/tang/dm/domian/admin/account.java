package cn.tang.dm.domian.admin;

import cn.tang.dm.domian.common.BaseDomain;

import java.util.Date;

/**
 * 账号实体
 *
 * @author tangzeqian
 * @create 2016-02-25  21:49
 */
public class Account extends BaseDomain {

    /**
     * 账号姓名
     */
    private String name;
    /**
     * 账号密码
     */
    private String passWord;
    /**
     * 角色id
     */
    private long roleId;
    /**
     * email
     */
    private String email;
    /**
     * 手机号码
     */
    private long phone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 账号状态
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
        return "Account{" +
                "name='" + name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", roleId=" + roleId +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", sex='" + sex + '\'' +
                ", state=" + state +
                ", date=" + date +
                "} " + super.toString();
    }
}
