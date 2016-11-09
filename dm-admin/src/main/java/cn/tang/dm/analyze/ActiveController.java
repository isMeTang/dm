package cn.tang.dm.analyze;

import cn.tang.dm.common.ExcelUtils;
import cn.tang.dm.common.ProvinceConst;
import cn.tang.dm.common.Tools;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.domian.result.ProvinceActiveResult;
import cn.tang.dm.service.analyze.IActiveService;
import cn.tang.dm.service.analyze.IRegisterService;
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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 活跃情况控制器
 *
 * @author tangzeqian
 * @create 2016-03-15  10:21
 */
@Controller
@RequestMapping("/dm/active")
public class ActiveController {
    @Autowired
    IActiveService activeService;
    @Autowired
    IRegisterService registerService;

    /**
     * 所有月份
     */
    @ResponseBody
    @RequestMapping("/allYear")
    public Result allYear() {
        List<Long> list = activeService.queryAllYear();
        return new Result("success", 1, list);
    }

    /**
     * 活跃峰值
     */
    @ResponseBody
    @RequestMapping("/peak")
    public Result peak(long month) {
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        Active maxActiveByP = new Active();
        long maxActive = 0;
        long sumByMonth = 0l;
        long sumByYear = 0l;
        long sumRegister = 0l;
        Map<Long, Long> dayMap = new HashMap<Long, Long>();

        List<Active> list = activeService.queryList(map, "value", "DESC");
        if (!Tools.isNull(list)) {
            //本月日活跃省维度峰值
            maxActiveByP = list.get(0);
            maxActiveByP.setKpi(ProvinceConst.map.get((int)maxActiveByP.getProvinceId()));
            //本月累计活跃值
            for (Active item : list) {
                sumByMonth += item.getValue();
                //本月日活跃峰值
                long dateId = item.getDate();
                if (dayMap.containsKey(dateId)) {
                    long value = dayMap.get(dateId) + item.getValue();
                    dayMap.put(dateId, value);
                } else {
                    dayMap.put(dateId, item.getValue());
                }
            }
            maxActive = Tools.getMaxValue(dayMap);
        }
        //年度累计活跃值
        map.clear();
        startDate = (month / 100) * 10000 + 101;
        endDate = (month / 100) * 10000 + 1231;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        list = activeService.queryList(map, "id", "ASC");
        if (!Tools.isNull(list)) {
            for (Active item : list) {
                sumByYear += item.getValue();
            }
        }
        //截止本月注册总数
        map.clear();
        map.put("endDate", month);
        sumRegister = registerService.querySumForMonth(map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("maxActiveByP", maxActiveByP);
        resultMap.put("maxActive", maxActive);
        resultMap.put("sumByMonth", sumByMonth);
        resultMap.put("sumByYear", sumByYear);
        resultMap.put("sumRegister", sumRegister);
        return new Result("success", 1, resultMap);
    }

    /**
     * 当前月每天活跃统计
     */
    @ResponseBody
    @RequestMapping("/dayActive")
    public Result dayActive(long month) {
        List<Long> resultList = new ArrayList<Long>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Active> list = activeService.queryList(map, "id", "ASC");
        Map<String, Long> dayMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            //统计每天全国累计活跃值
            for (Active item : list) {
                //本月日活跃峰值
                long dateId = item.getDate();
                if (dayMap.containsKey(dateId)) {
                    long value = dayMap.get(dateId) + item.getValue();
                    dayMap.put(dateId + "", value);
                } else {
                    dayMap.put(dateId + "", item.getValue());
                }
            }
        }
        //获取当月所有日期
        List<String> dateList = Tools.getDaysOfMonth(month, "yyyyMMdd");
        for (String date : dateList) {
            if (dayMap.containsKey(date)) {
                resultList.add(dayMap.get(date));
            } else {
                resultList.add(0l);
            }
        }
        return new Result("success", 1, resultList);
    }

    /**
     * 当前年每月活跃统计
     */
    @ResponseBody
    @RequestMapping("/monthActive")
    public Result monthActive(long month) {
        List<Long> resultList = new ArrayList<Long>();
        Map<String, Object> map = new HashMap<String, Object>();
        long year = month / 100;
        long startDate = year * 10000 + 101;
        long endDate = year * 10000 + 1231;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Active> list = activeService.queryList(map, "id", "ASC");
        Map<String, Long> monthMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            //统计每月全国累计活跃值
            for (Active item : list) {
                //本月日活跃峰值
                String monthId = item.getDate() / 100 + "";
                if (monthMap.containsKey(monthId)) {
                    long value = monthMap.get(monthId) + item.getValue();
                    monthMap.put(monthId + "", value);
                } else {
                    monthMap.put(monthId + "", item.getValue());
                }
            }
        }
        //遍历12个月份
        for(int i = 1;i<=12;i++){
            String key = (year * 100 + i) + "";
            if (monthMap.containsKey(key)) {
                resultList.add(monthMap.get(key));
            } else {
                resultList.add(0l);
            }
        }
        return new Result("success", 1, resultList);
    }

    /**
     * 当前月各省份活跃统计
     */
    @ResponseBody
    @RequestMapping("/provinceActive")
    public Result provinceActive(long month) {
        List<ProvinceActiveResult> resultList = new ArrayList<ProvinceActiveResult>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Active> list = activeService.queryList(map, "id", "ASC");
        Map<String, Long> monthMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            //统计每月各省份累计活跃值
            for (Active item : list) {
                //本月日活跃峰值
                String provinceId = item.getProvinceId() + "";
                if (monthMap.containsKey(provinceId)) {
                    long value = monthMap.get(provinceId) + item.getValue();
                    monthMap.put(provinceId + "", value);
                } else {
                    monthMap.put(provinceId + "", item.getValue());
                }
            }
        }
        //遍历所有省份
        for(String key : monthMap.keySet()){
            ProvinceActiveResult item = new ProvinceActiveResult();
            item.setName(ProvinceConst.map.get(Integer.parseInt(key)));
            item.setValue(monthMap.get(key));
            resultList.add(item);
        }
        return new Result("success", 1, resultList);
    }

    /**
     * 分页查询当前月所有活跃记录表
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
        List<Active> list = activeService.queryPage(map, "date,province_id", "ASC", begin, lim);
        if(list != null && list.size() >0){
            for(Active item : list){
                int id = (int) item.getProvinceId();
                item.setKpi(ProvinceConst.map.get(id));
            }
        }
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
        long count = activeService.count(map);
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
        List<Active> list = activeService.queryList(map,"id","ASC");
        String filePath = request.getSession().getServletContext().getRealPath("/") + "temp\\temp.xlsx";
        Workbook workBook = new XSSFWorkbook(filePath);
        Sheet sh = workBook.getSheetAt(0);
        ExcelUtils.setCellValue(sh.getRow(0).getCell(0), "活跃汇总表");///设置标题
        CellStyle style = sh.getRow(2).getCell(0).getCellStyle();
        int row = 2;//从第五行开始写数据
        try {
            for (Active item : list) {
                if(null == item)continue;
                int provinceId = (int) item.getProvinceId();
                ExcelUtils.setValue(sh,row,0,item.getId()+"",style);
                ExcelUtils.setValue(sh,row,1,ProvinceConst.map.get(provinceId),style);
                ExcelUtils.setValue(sh,row,2,item.getValue()+"",style);
                ExcelUtils.setValue(sh,row,3,item.getDate()+"",style);
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
        ExcelUtils.download(response, workBook,"活跃汇总表.xlsx");
    }

}
