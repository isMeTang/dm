package cn.tang.dm.domian.analyze;

import cn.tang.dm.domian.common.BaseDomain;

/**
 * 流量汇总实体
 * Created by tang on 2016/3/22.
 */
public class Flow extends BaseDomain{
    /*省份id*/
    private long provinceId;
    /*指标*/
    private String kpi;
    /*页面*/
    private String pageName;
    /*日期*/
    private long date;
    /*值*/
    private String value;

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

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Flow{" +
                "provinceId=" + provinceId +
                ", kpi='" + kpi + '\'' +
                ", pageName='" + pageName + '\'' +
                ", date=" + date +
                ", value='" + value + '\'' +
                "} " + super.toString();
    }
}
