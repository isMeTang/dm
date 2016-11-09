package cn.tang.dm.data;

import cn.tang.dm.common.ExcelUtils;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.analyze.Behavior;
import cn.tang.dm.domian.analyze.Flow;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.service.analyze.IActiveService;
import cn.tang.dm.service.analyze.IBehaviorService;
import cn.tang.dm.service.analyze.IFlowService;
import cn.tang.dm.service.analyze.IRegisterService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 数据入库控制器
 *
 * @author tangzeqian
 * @create 2016-03-07  14:20
 */
@Controller
@RequestMapping("/dm/push")
public class pushController {
    @Autowired
    IActiveService activeService;
    @Autowired
    IRegisterService registerService;
    @Autowired
    IBehaviorService behaviorService;
    @Autowired
    IFlowService flowService;

    @ResponseBody
    @RequestMapping("/uploadFile")
    public Result uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, String key) throws IOException {
        try {
            Workbook workBook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workBook.getSheetAt(0);
            if ("register".equals(key)) {
                addRegister(sheet);
            } else if ("active".equals(key)) {
                addActive(sheet);
            } else if ("behavior".equals(key)) {
                addBehavior(sheet);
            } else if ("flow".equals(key)) {
                addFlow(sheet);
            } else {
                return new Result("不支持此类型", 0, null);
            }
            return new Result("入库成功", 1, null);
        } catch (Exception e) {
            return new Result("数据格式异常", 0, null);
        }
    }

    /**
     * 活跃表数据入库
     *
     * @return
     */
    private void addActive(Sheet sheet) {
        int rownum = sheet.getLastRowNum();// 获取总行数
        for (int i = 1; i <= rownum; i++) {
            Row row = sheet.getRow(i);
            Active active = new Active();
            active.setProvinceId(Math.round(row.getCell(0).getNumericCellValue()));
            active.setDate(Math.round(row.getCell(1).getNumericCellValue()));
            active.setValue(Math.round(row.getCell(2).getNumericCellValue()));
            activeService.add(active);
        }
    }

    /**
     * 注册表数据入库
     *
     * @return
     */
    private void addRegister(Sheet sheet) {
        int rownum = sheet.getLastRowNum();// 获取总行数
        for (int i = 1; i <= rownum; i++) {
            Row row = sheet.getRow(i);
            Register register = new Register();
            register.setDate(Math.round(row.getCell(0).getNumericCellValue()));
            register.setProvinceId(Math.round(row.getCell(1).getNumericCellValue()));
            register.setSex(ExcelUtils.cellToString(row.getCell(2)));
            register.setAge((int) row.getCell(3).getNumericCellValue());
            register.setTypeId((int) row.getCell(4).getNumericCellValue());
            registerService.add(register);
        }
    }

    /**
     * 行为表数据入库
     *
     * @return
     */
    private void addBehavior(Sheet sheet) {
        int rownum = sheet.getLastRowNum();// 获取总行数
        for (int i = 1; i <= rownum; i++) {
            Row row = sheet.getRow(i);
            Behavior behavior = new Behavior();
            behavior.setDate(Math.round(row.getCell(0).getNumericCellValue()));
            behavior.setOs(ExcelUtils.cellToString(row.getCell(1)));
            behavior.setIsUser((int) row.getCell(2).getNumericCellValue());
            behavior.setIntoType((int) row.getCell(3).getNumericCellValue());
            behavior.setTime(ExcelUtils.cellToString(row.getCell(4)));
            behaviorService.add(behavior);
        }
    }

    /**
     * 流量表数据入库
     *
     * @return
     */
    private void addFlow(Sheet sheet) {
        int rownum = sheet.getLastRowNum();// 获取总行数
        for (int i = 1; i <= rownum; i++) {
            Row row = sheet.getRow(i);
            Flow flow = new Flow();
            flow.setDate(Math.round(row.getCell(0).getNumericCellValue()));
            flow.setValue(ExcelUtils.cellToString(row.getCell(1)));
            flow.setPageName(ExcelUtils.cellToString(row.getCell(2)));
            flow.setProvinceId(Math.round(row.getCell(3).getNumericCellValue()));
            flowService.add(flow);
        }
    }
}
