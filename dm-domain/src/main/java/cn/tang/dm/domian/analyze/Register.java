package cn.tang.dm.domian.analyze;

import cn.tang.dm.domian.common.BaseDomain;

/**
 * 注册表实体
 * Created by tang on 2016/3/20.
 */
public class Register extends BaseDomain{
    /*省份id*/
    private long provinceId;
    /*指标*/
    private String kpi;
    /*日期*/
    private long date;
    /*年龄*/
    private int age;
    /*性别*/
    private String sex;
    /*注册类型*/
    private int typeId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Register{" +
                "provinceId=" + provinceId +
                ", kpi='" + kpi + '\'' +
                ", date=" + date +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", typeId=" + typeId +
                ", value=" + value +
                "} " + super.toString();
    }
}
