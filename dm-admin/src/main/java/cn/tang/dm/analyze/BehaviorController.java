package cn.tang.dm.analyze;

import cn.tang.dm.common.ExcelUtils;
import cn.tang.dm.common.ProvinceConst;
import cn.tang.dm.common.Tools;
import cn.tang.dm.domian.analyze.Behavior;
import cn.tang.dm.domian.analyze.BehaviorSum;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.service.analyze.IBehaviorService;
import cn.tang.dm.service.analyze.IBehaviorSumService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行为情况控制器
 *
 * @author tangzeqian
 * @create 2016-03-15  10:21
 */
@Controller
@RequestMapping("/dm/behavior")
public class BehaviorController {
    @Autowired
    IBehaviorService behaviorService;
    @Autowired
    IBehaviorSumService behaviorSumService;

    /**
     * 所有月份
     */
    @ResponseBody
    @RequestMapping("/allYear")
    public Result allYear() {
        List<Long> list = behaviorSumService.queryAllYear();
        return new Result("success", 1, list);
    }

    /**
     * 行为峰值
     */
    @ResponseBody
    @RequestMapping("/peak")
    public Result peak(long month) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        long visitSumForMonth = 0;   //本月访问量 / 人次
        Double timeSumForMonth = 0d;   //本月访问总时长
        Double timeSumByOther = 0d;   //本月访客访问时长
        Double timeSumByUser = 0d;   //本月用户访问时长
        List<Long> intoList = new ArrayList<Long>(); //入口方式
        long type1 = 0l;    //地址栏跳转
        long type2 = 0l;    //搜索引擎进入
        long type3 = 0l;    //广告进入
        long type4 = 0l;    //客户端进入
        long type5 = 0l;    //其他方式

        List<Behavior> list = behaviorService.queryList(map, "id", "DESC");
        if (!Tools.isNull(list)) {
            for (Behavior item : list) {
                Double temp = Double.parseDouble(item.getTime());
                //本月累计访问人数
                visitSumForMonth++;
                //总时长
                timeSumForMonth += temp;
                if (item.getIsUser() == 1) {
                    timeSumByUser += temp;
                } else {
                    timeSumByOther += temp;
                }
                switch (item.getIntoType()) {
                    case 1:
                        type1++;
                        break;
                    case 2:
                        type2++;
                        break;
                    case 3:
                        type3++;
                        break;
                    case 4:
                        type4++;
                        break;
                    default:
                        type5++;
                        break;
                }
            }
            intoList.add(type1);
            intoList.add(type2);
            intoList.add(type3);
            intoList.add(type4);
            intoList.add(type5);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("visitSumForMonth", visitSumForMonth);
        resultMap.put("timeSumForMonth", timeSumForMonth);
        resultMap.put("timeSumByOther", timeSumByOther);
        resultMap.put("timeSumByUser", timeSumByUser);
        resultMap.put("intoList", intoList);
        return new Result("success", 1, resultMap);
    }

    /**
     * 当前月每天访问量统计
     */
    @ResponseBody
    @RequestMapping("/dayVisit")
    public Result dayVisit(long month) {
        List<Double> resultList = new ArrayList<Double>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Behavior> list = behaviorService.queryList(map, "id", "ASC");
        Map<String, Double> dayMap = new HashMap<String, Double>();
        if (!Tools.isNull(list)) {
            for (Behavior item : list) {
                String dateId = item.getDate() + "";
                if (dayMap.containsKey(dateId)) {
                    Double value = dayMap.get(dateId) + Double.parseDouble(item.getTime());
                    dayMap.put(dateId + "", value);
                } else {
                    dayMap.put(dateId + "", Double.parseDouble(item.getTime()));
                }
            }
        }
        //获取当月所有日期
        List<String> dateList = Tools.getDaysOfMonth(month, "yyyyMMdd");
        for (String date : dateList) {
            if (dayMap.containsKey(date)) {
                resultList.add(dayMap.get(date));
            } else {
                resultList.add(0d);
            }
        }
        return new Result("success", 1, resultList);
    }

    /**
     * 本年度每月各个时间段访问时长
     */
    @ResponseBody
    @RequestMapping("/timeVisit")
    public Result timeVisit(long month) {
        List<Double> resultList = new ArrayList<Double>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", month);
        BehaviorSum behaviorSum = behaviorSumService.queryOne(map);
        Double time1 = Double.parseDouble(behaviorSum.getTime1());
        Double time2 = Double.parseDouble(behaviorSum.getTime2());
        Double time3 = Double.parseDouble(behaviorSum.getTime3());
        Double time4 = Double.parseDouble(behaviorSum.getTime4());
        resultList.add(time1 + time2 + time3 + time4);
        resultList.add(time1);
        resultList.add(time2);
        resultList.add(time3);
        resultList.add(time4);
        return new Result("success", 1, resultList);

    }

    /**
     * 当前月每天访问量统计
     */
    @ResponseBody
    @RequestMapping("/osLine")
    public Result osLine(long month) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Behavior> list = behaviorService.queryList(map, "id", "ASC");
        List<Behavior> iosList = new ArrayList<Behavior>();
        List<Behavior> androidList = new ArrayList<Behavior>();
        List<Behavior> windowsList = new ArrayList<Behavior>();
        List<Behavior> otherList = new ArrayList<Behavior>();
        if (!Tools.isNull(list)) {
            for (Behavior item : list) {
                if ("ios".equals(item.getOs())) {
                    iosList.add(item);
                } else if ("android".equals(item.getOs())) {
                    androidList.add(item);
                } else if ("windows".equals(item.getOs())) {
                    windowsList.add(item);
                } else {
                    otherList.add(item);
                }
            }
        }
        Map<String, Long> iosMap = listToMap(iosList);
        Map<String, Long> androidMap = listToMap(androidList);
        Map<String, Long> windowsMap = listToMap(windowsList);
        Map<String, Long> otherMap = listToMap(otherList);
        List<Long> iosresult = new ArrayList<Long>();
        List<Long> windowsresult = new ArrayList<Long>();
        List<Long> androidresult = new ArrayList<Long>();
        List<Long> otherresult = new ArrayList<Long>();
        //获取当月所有日期
        List<String> dateList = Tools.getDaysOfMonth(month, "yyyyMMdd");
        for (String date : dateList) {
            if (iosMap.containsKey(date)) {
                iosresult.add(iosMap.get(date));
            } else {
                iosresult.add(0l);
            }
            if (androidMap.containsKey(date)) {
                androidresult.add(androidMap.get(date));
            } else {
                androidresult.add(0l);
            }
            if (windowsMap.containsKey(date)) {
                windowsresult.add(windowsMap.get(date));
            } else {
                windowsresult.add(0l);
            }
            if (otherMap.containsKey(date)) {
                otherresult.add(otherMap.get(date));
            } else {
                otherresult.add(0l);
            }
        }
        resultMap.put("ios",iosresult);
        resultMap.put("android",androidresult);
        resultMap.put("windows",windowsresult);
        resultMap.put("other",otherresult);
        return new Result("success", 1, resultMap);
    }

    /**
     * 分页查询当前月所有行为明细记录表
     */
    @ResponseBody
    @RequestMapping("/list")
    public Result list(long month, @RequestParam(defaultValue = "1") int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        long begin = (pageNo - 1) * 10;
        long lim = 10;
        List<Behavior> list = behaviorService.queryPage(map, "id,date", "ASC", begin, lim);
        return new Result("success", 1, list);
    }

    /**
     * 分页查询当前月所有活跃记录表
     */
    @ResponseBody
    @RequestMapping("/count")
    public Result count(long month) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        long count = behaviorService.count(map);
        return new Result("success", 1, count);
    }

    /**
     * 导出前月所有行为记录表
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @ResponseBody
    @RequestMapping("/down")
    public void down(HttpServletRequest request, HttpServletResponse response, long month) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<Behavior> list = behaviorService.queryList(map,"id","ASC");
        String filePath = request.getSession().getServletContext().getRealPath("/") + "temp\\temp3.xlsx";
        Workbook workBook = new XSSFWorkbook(filePath);
        Sheet sh = workBook.getSheetAt(0);
        ExcelUtils.setCellValue(sh.getRow(0).getCell(0), "用户行为明细表");///设置标题
        CellStyle style = sh.getRow(2).getCell(0).getCellStyle();
        int row = 2;//从第3行开始写数据
        try {
            for (Behavior item : list) {
                if(null == item)continue;
                String intoType = "";
                switch (item.getIntoType()) {
                    case 1:
                        intoType = "地址栏跳转";
                        break;
                    case 2:
                        intoType = "搜索引擎进入";
                        break;
                    case 3:
                        intoType = "广告进入";
                        break;
                    case 4:
                        intoType = "客户端进入";
                        break;
                    default:
                        intoType = "其他方式";
                        break;
                }
                String userType = item.getIsUser() == 1 ? "注册用户" : "访客";
                ExcelUtils.setValue(sh,row,0,item.getId()+"",style);
                ExcelUtils.setValue(sh,row,1,item.getDate()+"",style);
                ExcelUtils.setValue(sh,row,2,item.getTime(),style);
                ExcelUtils.setValue(sh,row,3,intoType,style);
                ExcelUtils.setValue(sh,row,4,item.getOs());
                ExcelUtils.setValue(sh,row,5,userType);
                row ++;
            }
        } catch (Exception e) {}
        Font font = workBook.createFont();
        font.setColor(Font.COLOR_RED);
        CellStyle newStyle = workBook.createCellStyle();
        newStyle.cloneStyleFrom(style);
        newStyle.setFont(font);
        newStyle.setAlignment(CellStyle.ALIGN_LEFT);
        ExcelUtils.mergeRegion(sh, row, (short) 0, row, (short) 9, null);
        ExcelUtils.setValue(sh, row,0,"",newStyle);
        sh.getRow(row).setHeight((short) (sh.getRow(row).getHeight() * 3));
        ExcelUtils.download(response, workBook,"用户行为明细表.xlsx");
    }

    /**
     * <Behavior>list转map
     */
    private Map<String, Long> listToMap(List<Behavior> list) {
        Map<String, Long> dayMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            for (Behavior item : list) {
                String dateId = item.getDate() + "";
                if (dayMap.containsKey(dateId)) {
                    long value = dayMap.get(dateId) + 1;
                    dayMap.put(dateId + "", value);
                } else {
                    dayMap.put(dateId + "", 1l);
                }
            }
        }
        return dayMap;
    }

}
