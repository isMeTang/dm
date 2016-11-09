package cn.tang.dm.domian.analyze;

import cn.tang.dm.domian.common.BaseDomain;

/**
 * 行为明细表实体
 * Created by tang on 2016/3/21.
 */
public class Behavior extends BaseDomain{
    /*指标*/
    private String kpi;
    /*时间*/
    private long date;
    /*在线时长*/
    private String time;
    /*操作系统*/
    private String os;
    /*访问类型*/
    private int intoType;
    /*是否注册用户*/
    private int isUser;

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public int getIntoType() {
        return intoType;
    }

    public void setIntoType(int intoType) {
        this.intoType = intoType;
    }

    public int getIsUser() {
        return isUser;
    }

    public void setIsUser(int isUser) {
        this.isUser = isUser;
    }

    @Override
    public String toString() {
        return "Behavior{" +
                "kpi='" + kpi + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", os='" + os + '\'' +
                ", intoType=" + intoType +
                ", isUser=" + isUser +
                "} " + super.toString();
    }
}
