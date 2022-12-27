package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class POITest {
    @Test
    public void test1() throws IOException {
        //加载指定文件 创建一个excel对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\84140\\Desktop\\a.XLSX")));
        //读取excel文件中第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历标签页获取每一行数据
        for (Row row : sheet) {
            //遍历行 获得每个单元格对象
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();
    }

    @Test
    public void Test2() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\84140\\Desktop\\a.XLSX")));

        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
//获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            //根据行号获取行对象
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (short j = 0; j < lastCellNum; j++) {
                String value = row.getCell(j).getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }
}
