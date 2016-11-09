package cn.tang.dm.domian.common;

/**
 * 基本返回实体
 *
 * @author tangzeqian
 * @create 2016-03-04  14:04
 */
public class Result {
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回状态
     */
    private int state;

    /** 返回对象 */
    private Object obj;

    public Result(String msg, int state, Object obj) {
        this.msg = msg;
        this.state = state;
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", state=" + state +
                ", baseDomain=" + obj +
                '}';
    }
}
