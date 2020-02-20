package com.itmuch.wenjie;

import com.cloud.util.ExcelUtil;
import com.cloud.util.ExportExcelHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itmuch.util.AddressUtils;
import com.itmuch.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExcelManual {

    private static Workbook workbook;

    private static List<UserInfo> users = Lists.newArrayList();


    public static void main(String args[]) throws Exception{
        Workbook workbook = ExcelUtils.getWorkbook("F:/名单.xls");
        getMainInfo(workbook.getSheetAt(0));
        checkUserInfo();
        createExcel();
//        Workbook review = ExportExcelHandler.exportDateToExcel("总览",users, ExportExcelHandler.Type.XLSX);
//        review.write(new FileOutputStream(new File("F:/总览.xls")));
    }

    private static void createExcel() throws Exception {
        for(UserInfo one: users){
            InputStream in = new FileInputStream(new File("F:/温杰.xlsx"));
            Workbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            Row name_IDCard = sheet.getRow(2);
            name_IDCard.getCell(2).setCellValue(one.getName());
            name_IDCard.getCell(7).setCellValue(one.getIDCard());
            Row phone_nativeHone = sheet.getRow(4);
            phone_nativeHone.getCell(2).setCellValue(one.getPhone());
            String nativeHome = AddressUtils.getShortAddressFromSourceAddress(one.getAddress());
            phone_nativeHone.getCell(7).setCellValue(nativeHome);
            Row address = sheet.getRow(5);
            address.getCell(2).setCellValue(one.getAddress());
            Row startPlace = sheet.getRow(7);
            startPlace.getCell(7).setCellValue(nativeHome);
            workbook.write(new FileOutputStream(new File("F:/wenjie_file/"+one.getName()+".xlsx")));
        }
    }


    private static void getMainInfo(Sheet sheet){
        int id = 1;
        for(int i = sheet.getLastRowNum(); i <= sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);
            UserInfo one = new UserInfo(id++);
            one.setName(row.getCell(0).getStringCellValue());
            String IDCard = row.getCell(2).getStringCellValue();
            one.setIDCard(IDCard);
            try{
                one.setAddress(row.getCell(1).getStringCellValue());
            }catch(Exception ex){
                one.setAddress("");
            }
            try{
                one.setPhone(row.getCell(3).getStringCellValue());
            }catch(Exception ex){
                one.setPhone("");
            }
            users.add(one);
        }
    }


    private static void checkUserInfo(){
        int maxId = 0;
        for(UserInfo one : users){
            System.out.println(one);
        }
        System.out.println(users.size());
    }

}
