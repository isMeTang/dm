package cn.tang.dm.domian.result;

/**
 * 省份流量统计返回实体
 *
 * @author tangzeqian
 * @create 2016-03-15  16:44
 */
public class ProvinceFlowResult {
    /*省份*/
    private String name;
    /*值*/
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.indexOf("省") > 0){
            name = name.substring(0,name.indexOf("省"));
        }
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
