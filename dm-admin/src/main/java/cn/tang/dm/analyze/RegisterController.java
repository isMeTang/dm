package cn.tang.dm.analyze;

import cn.tang.dm.common.ExcelUtils;
import cn.tang.dm.common.ProvinceConst;
import cn.tang.dm.common.Tools;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.domian.result.ProvinceActiveResult;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册情况控制器
 *
 * @author tangzeqian
 * @create 2016-03-15  10:21
 */
@Controller
@RequestMapping("/dm/register")
public class RegisterController {
    @Autowired
    IRegisterService registerService;

    /**
     * 所有月份
     */
    @ResponseBody
    @RequestMapping("/allYear")
    public Result allYear() {
        List<Long> list = registerService.queryAllYear();
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

        long maxForDay = 0;   //本月最高天注册人数
        long sumForMonth = 0l;//本月累计注册人数
        long sumForYear = 0l;//本年累计注册人数
        long manSum = 0l;   //本月注册人数（男）
        long grilSum = 0l; //本月注册人数（女）
        long level1 = 0l;  //小于18岁
        long level2 = 0l;  //18~30
        long level3 = 0l;  //31~50
        long level4 = 0l;  //50~
        List<Long> ageList = new ArrayList<Long>(); //年龄段汇总
        Map<Long, Long> dayMap = new HashMap<Long, Long>();

        List<Register> list = registerService.queryList(map, "id", "DESC");
        if (!Tools.isNull(list)) {
            for (Register item : list) {
                //本月累计注册人数
                sumForMonth ++;
                //性别统计
                if(item.getSex().equals("1") || item.getSex().equals("男")){
                    manSum ++;
                } else{
                    grilSum ++;
                }
                //年龄统计
                int age = item.getAge();
                if(age < 18){
                    level1 ++;
                } else if(age >= 18 && age <=30){
                    level2 ++;
                } else if(age > 30 && age <=50){
                    level3 ++;
                } else{
                    level4 ++;
                }
                //本月日注册峰值
                long dateId = item.getDate();
                if (dayMap.containsKey(dateId)) {
                    long value = dayMap.get(dateId) + 1;
                    dayMap.put(dateId, value);
                } else {
                    dayMap.put(dateId, 1l);
                }
            }
            maxForDay = Tools.getMaxValue(dayMap);
            ageList.clear();
            ageList.add(level1);
            ageList.add(level2);
            ageList.add(level3);
            ageList.add(level4);
        }
        //年度累计活跃值
        map.clear();
        startDate = (month / 100) * 10000 + 101;
        endDate = (month / 100) * 10000 + 1231;
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        sumForYear = registerService.count(map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("maxForDay", maxForDay);
        resultMap.put("sumForMonth", sumForMonth);
        resultMap.put("sumForYear", sumForYear);
        resultMap.put("manSum", manSum);
        resultMap.put("grilSum", grilSum);
        resultMap.put("ageList", ageList);
        return new Result("success", 1, resultMap);
    }

    /**
     * 当前月每天活跃统计
     */
    @ResponseBody
    @RequestMapping("/dayRegister")
    public Result dayActive(long month) {
        List<Long> resultList = new ArrayList<Long>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Register> list = registerService.queryList(map, "id", "ASC");
        Map<String, Long> dayMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            //统计每天全国累计活跃值
            for (Register item : list) {
                //本月日活跃峰值
                String dateId = item.getDate() + "";
                if (dayMap.containsKey(dateId)) {
                    long value = dayMap.get(dateId) + 1;
                    dayMap.put(dateId + "", value);
                } else {
                    dayMap.put(dateId + "", 1l);
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
    @RequestMapping("/monthRegister")
    public Result monthActive(long month) {
        List<Long> resultList = new ArrayList<Long>();
        Map<String, Object> map = new HashMap<String, Object>();
        long year = month / 100;
        long startDate = year * 10000 + 101;
        long endDate = year * 10000 + 1231;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Register> list = registerService.queryList(map, "id", "ASC");
        Map<String, Long> monthMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            //统计每月全国累计活跃值
            for (Register item : list) {
                //本月日活跃峰值
                String monthId = item.getDate() / 100 + "";
                if (monthMap.containsKey(monthId)) {
                    long value = monthMap.get(monthId) + 1;
                    monthMap.put(monthId + "", value);
                } else {
                    monthMap.put(monthId + "", 1l);
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
    @RequestMapping("/provinceRegister")
    public Result provinceActive(long month) {
        List<ProvinceActiveResult> resultList = new ArrayList<ProvinceActiveResult>();
        Map<String, Object> map = new HashMap<String, Object>();
        long startDate = month * 100 + 1;
        long endDate = month * 100 + 31;
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Register> list = registerService.queryList(map, "id", "ASC");
        Map<String, Long> monthMap = new HashMap<String, Long>();
        if (!Tools.isNull(list)) {
            //统计每月各省份累计活跃值
            for (Register item : list) {
                //本月日活跃峰值
                String provinceId = item.getProvinceId() + "";
                if (monthMap.containsKey(provinceId)) {
                    long value = monthMap.get(provinceId) + 1;
                    monthMap.put(provinceId + "", value);
                } else {
                    monthMap.put(provinceId + "", 1l);
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
        List<Register> list = registerService.queryPage(map, "province_id,date", "ASC", begin, lim);
        if(list != null && list.size() >0){
            for(Register item : list){
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
        long count = registerService.count(map);
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
        List<Register> list = registerService.queryList(map,"id","ASC");
        String filePath = request.getSession().getServletContext().getRealPath("/") + "temp\\temp2.xlsx";
        Workbook workBook = new XSSFWorkbook(filePath);
        Sheet sh = workBook.getSheetAt(0);
        ExcelUtils.setCellValue(sh.getRow(0).getCell(0), "注册明细表");///设置标题
        CellStyle style = sh.getRow(2).getCell(0).getCellStyle();
        int row = 2;//从第五行开始写数据
        try {
            for (Register item : list) {
                if(null == item)continue;
                int provinceId = (int) item.getProvinceId();
                String sex = item.getSex().equals("1") || item.getSex().equals("男")? "男" : "女";
                String type = "";
                switch (item.getTypeId()) {
                    case 100:
                        type = "本站注册";
                        break;
                    case 101:
                        type = "QQ注册";
                        break;
                    case 102:
                        type = "邮箱注册";
                        break;
                    default:
                        type = "其他方式";
                        break;
                }
                ExcelUtils.setValue(sh,row,0,item.getId()+"",style);
                ExcelUtils.setValue(sh,row,1,ProvinceConst.map.get(provinceId),style);
                ExcelUtils.setValue(sh,row,2,item.getDate()+"",style);
                ExcelUtils.setValue(sh,row,3,item.getAge()+"",style);
                ExcelUtils.setValue(sh,row,4,sex);
                ExcelUtils.setValue(sh,row,5,type);
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
        ExcelUtils.download(response, workBook,"注册明细表.xlsx");
    }
}
