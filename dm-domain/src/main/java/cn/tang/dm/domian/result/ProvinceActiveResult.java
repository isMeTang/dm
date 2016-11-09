package cn.tang.dm.domian.result;

/**
 * 省份活跃返回实体
 *
 * @author tangzeqian
 * @create 2016-03-15  16:44
 */
public class ProvinceActiveResult {
    /*省份*/
    private String name;
    /*值*/
    private long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.indexOf("省") > 0){
            name = name.substring(0,name.indexOf("省"));
        }
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
