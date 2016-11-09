/*
package cn.tang.dm.common;

import cn.tang.dm.domian.result.ProvinceActiveResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
*/

/**
 * Created by tang on 2016/3/20.
 */
public class MyTest {
    /*@Test
    public void testProvinceConst() {
        System.out.println(ProvinceConst.map.get(15));
    }

    @Test
    public void testSubString() {
        ProvinceActiveResult provinceActiveResult = new ProvinceActiveResult();
        provinceActiveResult.setName("内蒙古");
    }

    @Test
    public void readExcle() {
        //http://7xkeaf.com1.z0.glb.clouddn.com/temp.xlsx
        //String filePath = "C:\\temp.xlsx";
        // Workbook workBook = new XSSFWorkbook(filePath);
        try {
            URL url = new URL("http://7xkeaf.com1.z0.glb.clouddn.com/text.xlsx");
            Workbook workBook = new XSSFWorkbook(url.openStream());
            Sheet sheet = workBook.getSheetAt(0);
            int rownum = sheet.getLastRowNum();// 获取总行数
            for (int i = 0; i <= rownum; i++) {
                Row row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    System.out.println(j);
                    Cell celldata = row.getCell(j);
                    System.out.println(ExcelUtils.cellToString(celldata));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
