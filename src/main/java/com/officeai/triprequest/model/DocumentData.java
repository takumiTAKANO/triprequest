package com.officeai.triprequest.model;

import com.officeai.triprequest.Document;
import com.officeai.triprequest.util.KieServiceClient;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.SheetUtil;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Data
public class DocumentData {
    String tripClassNew;
    String fundClass;
    Date startDate;
    Date endDate;
    String destinationAndReason;
    String teikiStart;
    String teikiEnd;
    String businessPersonBelong;
    String businessPersonStaffNumber;
    String businessPersonJobTitle;
    String businessPersonName;
    String businessPersonExtension;
    String businessTravelerBelong;
    String businessTravelerStaffNumber;
    String businessTravelerJobTitle;
    String businessTravelerName;
    String businessTravelerExtension;
    ArrayList<Result> resultList;
    Boolean getOnPlane;
    Boolean getOnTrain;
    String remarks;

    String document;
    String documentDescription;
    String fileName1;
    String fileName2;
        public void export() {
            Document doc = new Document();

            doc.set出張者職種(businessTravelerJobTitle);
            doc.set航空機を利用するか(getOnPlane);
            doc.set電車を利用するか(getOnTrain);

            KieCommands commandFactory = KieServices.Factory.get().getCommands();
            Command<?> insert = commandFactory.newInsert(doc, "document");
            Command<?> fireAllRules = commandFactory.newFireAllRules();
            Command<?> batchCommand = commandFactory.newBatchExecution(Arrays.asList(insert, fireAllRules));
            ExecutionResults results = KieServiceClient.execute(batchCommand);
            Document resultD = (Document) results.getValue("document");

            this.setDocument(resultD.get必要書類());
            this.setDocumentDescription(resultD.get説明());
        try {
            String home = System.getProperty( "user.home");
            InputStream inp = new FileInputStream(home+"/triprequest/triprequest-master/shinseisho.xlsx");
            InputStream inp2 = new FileInputStream(home+"/triprequest/triprequest-master/shinseisho1.xlsx");
            Workbook wb = WorkbookFactory.create(inp);
            Workbook wb2 = WorkbookFactory.create(inp2);
            Sheet sheet = wb.getSheetAt(0);
            Sheet sheet2 = wb2.getSheetAt( 0);

            Font font1 = wb.createFont();
            font1.setFontHeightInPoints((short)6);
            font1.setFontName("ＭＳ 明朝");

            Font font3 = wb2.createFont();
            font3.setFontHeightInPoints((short)11);
            font3.setFontName("ＭＳ 明朝");

            CellStyle style1 = wb.createCellStyle();
            style1.setFont(font1);
            style1.setWrapText(true);
            style1.setBorderBottom(BorderStyle.THIN);

            Font font2 = wb.createFont();
            font2.setFontHeightInPoints((short)9);
            font2.setFontName("ＭＳ Ｐゴシック");

            CellStyle style2 = wb.createCellStyle();
            style2.setFont(font2);
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setVerticalAlignment(VerticalAlignment.CENTER );

            CellStyle style3 = wb2.createCellStyle();
            style3.setFont(font3);
            style3.setWrapText(true);
            style3.setBorderBottom(BorderStyle.THIN);

//            CellStyle style4 = wb.createCellStyle();

//            style4.setFillForegroundColor(IndexedColors.PINK.getIndex());
//            style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);



            //事業担当者
            Cell cell1 = SheetUtil.getCellWithMerges(sheet, 7, 7);
            cell1.setCellValue(businessPersonBelong);//所属
            Cell cell2 = SheetUtil.getCellWithMerges(sheet, 7, 25);
            cell2.setCellValue(businessPersonStaffNumber);//教職員番号
            Cell cell3 = SheetUtil.getCellWithMerges(sheet, 8, 7);
            cell3.setCellValue(businessPersonJobTitle);//職名
            Cell cell4 = SheetUtil.getCellWithMerges(sheet, 9, 7);
            cell4.setCellValue(businessPersonName);//氏名
            Cell cell5 = SheetUtil.getCellWithMerges(sheet, 9, 23);
            cell5.setCellValue(businessPersonExtension);//連絡先

            //出張者
            Cell cell6 = SheetUtil.getCellWithMerges(sheet, 10, 7);
            cell6.setCellValue(businessTravelerBelong);//所属
            Cell cell7 = SheetUtil.getCellWithMerges(sheet, 10, 25);
            cell7.setCellValue(businessTravelerStaffNumber);//教職員番号
            Cell cell8 = SheetUtil.getCellWithMerges(sheet, 11, 7);
            cell8.setCellValue(businessTravelerJobTitle);//職名
            Cell cell9 = SheetUtil.getCellWithMerges(sheet, 12, 7);
            cell9.setCellValue(businessTravelerName);//氏名
            Cell cell10 = SheetUtil.getCellWithMerges(sheet, 12, 23);
            cell10.setCellValue(businessTravelerExtension);//連絡先

            Cell cell11 = SheetUtil.getCellWithMerges(sheet, 6, 1);
            cell11.setCellValue(tripClassNew);

            Cell cell12 = SheetUtil.getCellWithMerges(sheet, 13, 11);
            cell12.setCellValue(fundClass);
            Cell cell13 = SheetUtil.getCellWithMerges(sheet, 15, 5);
            cell13.setCellValue(startDate);
            Cell cell14 = SheetUtil.getCellWithMerges(sheet, 15, 12);
            cell14.setCellValue(endDate);
            Cell cell15 = SheetUtil.getCellWithMerges(sheet, 16, 5);
            cell15.setCellValue(destinationAndReason);
            Cell cell16 = SheetUtil.getCellWithMerges(sheet, 27, 5);
            cell16.setCellValue(teikiStart);
            Cell cell17 = SheetUtil.getCellWithMerges(sheet, 27, 14);
            cell17.setCellValue(teikiEnd);
            Cell cell33 = SheetUtil.getCellWithMerges(sheet,43 , 4);
            cell33.setCellValue(remarks);

            cell16.setCellStyle(style2);
            cell17.setCellStyle(style2);

            int i = 0;
            int accommodation = 0;
            int dailyAllowance = 0;
            int travelerFare = 0;
            int venderFare = 0;
            for (Result data: resultList) {
                    accommodation += data.accommodation;
                    dailyAllowance += data.dailyAllowance;
                if (data.payment.equals("traveler") ) {
                    travelerFare += data.fare;
                }else if (data.payment.equals("vender") ){
                    venderFare += data.fare;
                }
                if (i<9) {
                    Cell cell18 = SheetUtil.getCellWithMerges(sheet, 31 + i, 0);
                    cell18.setCellValue(data.month);
                    Cell cell19 = SheetUtil.getCellWithMerges(sheet, 31 + i, 2);
                    cell19.setCellValue(data.date);
                    Cell cell20 = SheetUtil.getCellWithMerges(sheet, 31 + i, 4);
                    cell20.setCellValue(data.body);
                    if (data.payment.equals("traveler") ){
                        Cell cell21 = SheetUtil.getCellWithMerges(sheet, 31 + i, 14);
                    cell21.setCellValue(data.fare);
                    }
                    else if (data.payment.equals("vender") ) {
                        Cell cell21 = SheetUtil.getCellWithMerges(sheet, 31 + i, 16);
                        cell21.setCellValue(data.fare);
                    }
                    Cell cell22 = SheetUtil.getCellWithMerges(sheet, 31 + i, 18);
                    cell22.setCellValue(data.accommodation);
                    Cell cell23 = SheetUtil.getCellWithMerges(sheet, 31 + i, 22);
                    cell23.setCellValue(data.dailyAllowance);

                    cell20.setCellStyle(style1);

                    Row row1 = sheet.getRow(31 + i);

                    if (data.body.length() >= 70 && data.body.length() < 105) {
                        row1.setHeightInPoints(35);
                    } else if (data.body.length() >= 105 && data.body.length() < 140) {
                        row1.setHeightInPoints(45);
                    } else if (data.body.length() >= 140 && data.body.length() < 175) {
                        row1.setHeightInPoints(55);
                    } else if (data.body.length() >= 175 && data.body.length() < 210) {
                        row1.setHeightInPoints(65);
                    } else if (data.body.length() >= 210 && data.body.length() < 245) {
                        row1.setHeightInPoints(75);
                    } else if (data.body.length() >= 245 && data.body.length() < 280) {
                        row1.setHeightInPoints(85);
                    } else if (data.body.length() >= 280) {
                        row1.setHeightInPoints(95);
                    }
                }else{
                    if (data.month !="") {
                        Cell cell18 = SheetUtil.getCellWithMerges(sheet2, -1 + i, 0);
                        cell18.setCellValue(data.month + "月" + data.date + "日");
                    }
                    Cell cell20 = SheetUtil.getCellWithMerges(sheet2, -1 + i, 2);
                    cell20.setCellValue(data.body);
                    Cell cell21 = SheetUtil.getCellWithMerges(sheet2, -1 + i, 3);
                    cell21.setCellValue(data.fare);
                    Cell cell22 = SheetUtil.getCellWithMerges(sheet2, -1 + i, 7);
                    cell22.setCellValue(data.accommodation);
                    Cell cell23 = SheetUtil.getCellWithMerges(sheet2, -1 + i, 11);
                    cell23.setCellValue(data.dailyAllowance);

                    cell20.setCellStyle(style3);
                }
                i++;

            }
            if (i <10){
                Cell cell24 = SheetUtil.getCellWithMerges(sheet, 40, 14);
                cell24.setCellValue(travelerFare);
                Cell cell34 = SheetUtil.getCellWithMerges(sheet, 40, 16);
                cell34.setCellValue(venderFare);
                Cell cell25 = SheetUtil.getCellWithMerges(sheet, 40, 18);
                cell25.setCellValue(accommodation);
                Cell cell26 = SheetUtil.getCellWithMerges(sheet, 40, 22);
                cell26.setCellValue(dailyAllowance);
            }else{
                Cell cell24 = SheetUtil.getCellWithMerges(sheet2, 14, 3);
                cell24.setCellValue(travelerFare);
                Cell cell35 = SheetUtil.getCellWithMerges(sheet2, 14, 5);
                cell24.setCellValue(venderFare);
                Cell cell25 = SheetUtil.getCellWithMerges(sheet2, 14, 7);
                cell25.setCellValue(accommodation);
                Cell cell26 = SheetUtil.getCellWithMerges(sheet2, 14, 11);
                cell26.setCellValue(dailyAllowance);
                Cell cell30 = SheetUtil.getCellWithMerges(sheet2, 3, 2);
                cell30.setCellValue(businessTravelerBelong+"・"+businessTravelerName);

            }


            Cell cell27 = SheetUtil.getCellWithMerges(sheet, 17, 5);
            cell27.setCellValue("（１）");

            Cell cell28 = SheetUtil.getCellWithMerges(sheet, 5, 26);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            cell28.setCellValue(sdf.format(cal.getTime()));

            Cell cell29 = SheetUtil.getCellWithMerges(sheet, 15, 19);
            cell29.setCellValue(differenceDays(endDate,startDate));

            Cell cell31 = SheetUtil.getCellWithMerges(sheet, 45, 5);
            cell31.setCellValue(travelerFare+accommodation+dailyAllowance);
            Cell cell36 = SheetUtil.getCellWithMerges(sheet, 45, 15);
            cell36.setCellValue(venderFare);
            Cell cell32 = SheetUtil.getCellWithMerges(sheet, 45, 22);
            cell32.setCellValue(travelerFare+venderFare+accommodation+dailyAllowance);

            Date timestamp = new Date();
            int hash = timestamp.hashCode();

//            fileName1 ="triprequest_"+hash+"1.xlsx";
            this.setFileName1("triprequest_"+hash+"1.xlsx");
            OutputStream fileOut = new FileOutputStream(home+"/triprequest/triprequest-frontend-master/dist/doc/"+fileName1);
            wb.write(fileOut);
            if (i>=10){
//                fileName2 ="triprequest"+hash+"2.xlsx";
                this.setFileName2("triprequest"+hash+"2.xlsx");
                OutputStream fileOut2 = new FileOutputStream(home+"/triprequest/triprequest-frontend-master/dist/doc/"+fileName2);
                wb2.write(fileOut2);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            Command<?> dispose = commandFactory.newDispose();
            KieServiceClient.execute(dispose);


    }

    private int differenceDays(Date endDate, Date startDate) {
            long datetime1 = endDate.getTime();
            long datetime2 = startDate.getTime();
            long one_date_time = 1000*60*60*24;
            long diffDays = (datetime1-datetime2)/one_date_time+1;
            return (int)diffDays;
    }

}
