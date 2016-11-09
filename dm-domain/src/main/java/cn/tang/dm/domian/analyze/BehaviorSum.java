package cn.tang.dm.domian.analyze;

import cn.tang.dm.domian.common.BaseDomain;

/**
 * 行为汇总表实体
 * Created by tang on 2016/3/21.
 */
public class BehaviorSum extends BaseDomain{
    /*指标*/
    private String kpi;
    /*时间*/
    private long date;
    /*0-8点访问量*/
    private String time1;
    /*8-12点访问量*/
    private String time2;
    /*12-19点访问量*/
    private String time3;
    /*19-24点访问量*/
    private String time4;

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

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    @Override
    public String toString() {
        return "BehaviorSum{" +
                "kpi='" + kpi + '\'' +
                ", date=" + date +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", time3='" + time3 + '\'' +
                ", time4='" + time4 + '\'' +
                "} " + super.toString();
    }
}
