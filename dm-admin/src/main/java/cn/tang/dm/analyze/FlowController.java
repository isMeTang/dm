package cn.tang.dm.analyze;

import cn.tang.dm.common.ExcelUtils;
import cn.tang.dm.common.ProvinceConst;
import cn.tang.dm.common.Tools;
import cn.tang.dm.domian.analyze.Behavior;
import cn.tang.dm.domian.analyze.Flow;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.domian.result.ProvinceFlowResult;
import cn.tang.dm.service.analyze.IFlowService;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流量统计汇总控制器
 *
 * @author tangzeqian
 * @create 2016-03-15  10:21
 */
@Controller
@RequestMapping("/dm/flow")
public class FlowController {
    @Autowired
    IFlowService flowService;

    /**
     * 所有月份
     */
    @ResponseBody
    @RequestMapping("/allYear")
    public Result allYear() {
        List<Long> list = flowService.queryAllYear();
        return new Result("success", 1, list);
    }

    /**
     * 各页面流量汇总
     */
    @ResponseBody
    @RequestMapping("/peak")
    public Result peak(long month) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        Double page1 = 0d;
        Double page2 = 0d;
        Double page3 = 0d;
        Double page4 = 0d;
        List<Flow> list = flowService.queryList(map, "id", "DESC");
        if (!Tools.isNull(list)) {
            for (Flow item : list) {
                Double temp = Double.parseDouble(item.getValue());
                if("A".equals(item.getPageName())){
                    page1 += temp;
                } else if("B".equals(item.getPageName())){
                    page2 += temp;
                } else if("C".equals(item.getPageName())){
                    page3 += temp;
                } else{
                    page4 += temp;
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("page1", page1);
        resultMap.put("page2", page2);
        resultMap.put("page3", page3);
        resultMap.put("page4", page4);
        return new Result("success", 1, resultMap);
    }

    /**
     * 当前月每天每个页面流量汇总统计
     */
    @ResponseBody
    @RequestMapping("/dayflow")
    public Result dayActive(long month) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Flow> list = flowService.queryList(map, "id", "ASC");
        List<Flow> list1 = new ArrayList<Flow>();
        List<Flow> list2 = new ArrayList<Flow>();
        List<Flow> list3 = new ArrayList<Flow>();
        List<Flow> list4 = new ArrayList<Flow>();
        if (!Tools.isNull(list)) {
            for (Flow item : list) {
                if("A".equals(item.getPageName())){
                    list1.add(item);
                } else if("B".equals(item.getPageName())){
                    list2.add(item);
                } else if("C".equals(item.getPageName())){
                    list3.add(item);
                } else{
                    list4.add(item);
                }
            }
        }
        Map<String, Double> map1 = listToMap(list1);
        Map<String, Double> map2 = listToMap(list2);
        Map<String, Double> map3 = listToMap(list3);
        Map<String, Double> map4 = listToMap(list4);
        List<Double> result1 = new ArrayList<Double>();
        List<Double> result2 = new ArrayList<Double>();
        List<Double> result3 = new ArrayList<Double>();
        List<Double> result4 = new ArrayList<Double>();
        //获取当月所有日期
        List<String> dateList = Tools.getDaysOfMonth(month, "yyyyMMdd");
        for (String date : dateList) {
            if (map1.containsKey(date)) {
                result1.add(map1.get(date));
            } else {
                result1.add(0d);
            }
            if (map2.containsKey(date)) {
                result2.add(map2.get(date));
            } else {
                result2.add(0d);
            }
            if (map3.containsKey(date)) {
                result3.add(map3.get(date));
            } else {
                result3.add(0d);
            }
            if (map4.containsKey(date)) {
                result4.add(map4.get(date));
            } else {
                result4.add(0d);
            }
        }
        resultMap.put("page1",result1);
        resultMap.put("page2",result2);
        resultMap.put("page3",result3);
        resultMap.put("page4",result4);
        return new Result("success", 1, resultMap);
    }

    /**
     * 当前月各省份流量统计
     */
    @ResponseBody
    @RequestMapping("/provinceFlow")
    public Result provinceActive(long month) {
        List<ProvinceFlowResult> resultList = new ArrayList<ProvinceFlowResult>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Flow> list = flowService.queryList(map, "id", "ASC");
        Map<String, Double> monthMap = new HashMap<String, Double>();
        if (!Tools.isNull(list)) {
            //统计每月各省份累计活跃值
            for (Flow item : list) {
                Double temp = Double.parseDouble(item.getValue());
                //本月日活跃峰值
                String provinceId = item.getProvinceId() + "";
                if (monthMap.containsKey(provinceId)) {
                    Double value = monthMap.get(provinceId) + temp;
                    monthMap.put(provinceId + "", value);
                } else {
                    monthMap.put(provinceId + "", temp);
                }
            }
        }
        //遍历所有省份
        for(String key : monthMap.keySet()){
            ProvinceFlowResult item = new ProvinceFlowResult();
            item.setName(ProvinceConst.map.get(Integer.parseInt(key)));
            item.setValue(monthMap.get(key));
            resultList.add(item);
        }
        return new Result("success", 1, resultList);
    }

    /**
     * 分页查询当前月所有流量汇总记录表
     */
    @ResponseBody
    @RequestMapping("/list")
    public Result list(long month,@RequestParam(defaultValue = "1") int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        long begin = (pageNo - 1) * 10;
        long lim = 10;
        List<Flow> list = flowService.queryPage(map, "provinceId,date", "ASC", begin, lim);
        if(list != null && list.size() >0){
            for(Flow item : list){
                int id = (int) item.getProvinceId();
                item.setKpi(ProvinceConst.map.get(id));
            }
        }
        return new Result("success", 1, list);
    }

    /**
     * 分页查询当前月所有流量记录数
     */
    @ResponseBody
    @RequestMapping("/count")
    public Result count(long month) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        long count = flowService.count(map);
        return new Result("success", 1, count);
    }

    /**
     * 导出前月所有活跃记录表
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
        List<Flow> list = flowService.queryList(map,"id","ASC");
        String filePath = request.getSession().getServletContext().getRealPath("/") + "temp\\temp4.xlsx";
        Workbook workBook = new XSSFWorkbook(filePath);
        Sheet sh = workBook.getSheetAt(0);
        ExcelUtils.setCellValue(sh.getRow(0).getCell(0), "流量统计汇总表");///设置标题
        CellStyle style = sh.getRow(2).getCell(0).getCellStyle();
        int row = 2;//从第3行开始写数据
        try {
            for (Flow item : list) {
                if(null == item)continue;
                int provinceId = (int) item.getProvinceId();
                Double value = Double.parseDouble(item.getValue()) /1024/1024/1024;
                DecimalFormat df = new DecimalFormat("0.00");
                ExcelUtils.setValue(sh,row,0,item.getId()+"",style);
                ExcelUtils.setValue(sh,row,1,ProvinceConst.map.get(provinceId),style);
                ExcelUtils.setValue(sh,row,2,item.getDate()+"",style);
                ExcelUtils.setValue(sh,row,3,item.getPageName(),style);
                ExcelUtils.setValue(sh,row,4,df.format(value)+"GB");
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
        ExcelUtils.download(response, workBook,"流量统计汇总表.xlsx");
    }

    /**
     * <Flow>list转map
     */
    private Map<String, Double> listToMap(List<Flow> list) {
        Map<String, Double> dayMap = new HashMap<String, Double>();
        if (!Tools.isNull(list)) {
            for (Flow item : list) {
                String dateId = item.getDate() + "";
                Double temp = Double.parseDouble(item.getValue());
                if (dayMap.containsKey(dateId)) {
                    Double value = dayMap.get(dateId) + temp;
                    dayMap.put(dateId + "", value);
                } else {
                    dayMap.put(dateId + "", temp);
                }
            }
        }
        return dayMap;
    }

}
