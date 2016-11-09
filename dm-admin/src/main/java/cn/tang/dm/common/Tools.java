package cn.tang.dm.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通用工具类
 *
 * @author tangzeqian
 * @create 2016-03-08  22:51
 */
public class Tools {
    /**
     * 对象转map
     */
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 判断list是否空
     */
    public static boolean isNull(List list) {
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 时间维度值转为日期
     */
    public static Date timeIdToDate(Long timeId, String format) {
        try {
            return new SimpleDateFormat(format).parse(timeId.toString());
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取月份的某一天的日期
     */
    public static  Date getDayOfMonth(Long month,int number)
    {
        Date date=timeIdToDate(month,"yyyyMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int min=cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        if(number<min||number>max)
            throw new RuntimeException("No such number '"+number+"' in month "+month+".");
        cal.set(Calendar.DAY_OF_MONTH,number);
        return cal.getTime();
    }

    /**
     * 按指定格式获取月份的所有日期
     */
    public  static List<String> getDaysOfMonth(Long month,String format){
        Date monthDate=timeIdToDate(month, "yyyyMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int min=cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        DateFormat dateFormat=new SimpleDateFormat(format);
        List<String>list=new LinkedList<String>();
        for(int i=min;i<=max;i++){
            Date date=getDayOfMonth(month,i);
            String dateValue=dateFormat.format(date);
            list.add(dateValue);
        }
        return list;
    }

    /**
     * 求Map<K,V>中的最大值
     */
    public static Long getMaxValue(Map<Long, Long> map) {
        if (map == null) {
            return null;
        }
        long max = 0l;
        for (long obj : map.keySet()) {
            max = map.get(obj) >= max ? map.get(obj) : max;
        }
        return max;
    }
}
