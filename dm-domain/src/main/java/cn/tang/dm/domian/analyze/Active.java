package cn.tang.dm.domian.analyze;

import cn.tang.dm.domian.common.BaseDomain;

import java.util.Date;

/**
 * 活跃汇总表实体
 * @author tangzeqian
 * @create 2016-03-14  21:20
 */
public class Active extends BaseDomain{
    /*省份id*/
    private long provinceId;
    /*指标*/
    private String kpi;
    /*日期*/
    private long date;
    /*值*/
    private long value;

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Active{" +
                "provinceId=" + provinceId +
                ", kpi='" + kpi + '\'' +
                ", date=" + date +
                ", value=" + value +
                "} " + super.toString();
    }
}
