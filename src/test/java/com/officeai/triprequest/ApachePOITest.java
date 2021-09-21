package com.officeai.triprequest;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.SheetUtil;

import java.io.*;

public class ApachePOITest {
    public static void main(String[] args) {
        try {
            InputStream inp = new FileInputStream("shinseisho.xlsx");
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell1 = SheetUtil.getCellWithMerges(sheet, 7, 7);
            cell1.setCellValue("理工学部");//所属
            Cell cell2 = SheetUtil.getCellWithMerges(sheet, 7, 25);
            cell2.setCellValue("000000");//教職員番号
            Cell cell3 = SheetUtil.getCellWithMerges(sheet, 8, 7);
            cell3.setCellValue("助教");//職名
            Cell cell4 = SheetUtil.getCellWithMerges(sheet, 9, 7);
            cell4.setCellValue("山田太郎");//氏名
            Cell cell5 = SheetUtil.getCellWithMerges(sheet, 9, 23);
            cell5.setCellValue("00000");//氏名


            OutputStream fileOut = new FileOutputStream("workbook2.xlsx");
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
