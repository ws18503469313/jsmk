package com.itmuch.util;

import com.alibaba.fastjson.JSON;
import com.cloud.util.ExcelUtil;
import com.cloud.util.ExportExcel;
import com.cloud.util.ExportExcelHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class ZJExcel {

    private static final String SPIT = ",";

    public static void main3(String args[]) throws Exception{
        List<String> list = Lists.newArrayList("1", "ad12ec", "343c2");
        for(String str : list){
            if(str.equals("1")){
                list.remove(str);
            }
        }
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            if(it.next().equals("1"))
                it.remove();
        }
        System.out.println();
    }
    public static void main2(String args[]) throws Exception{
        String [] strs = new String []{"1", "343c2", "ad12ec"};
        List list = Lists.newArrayList("1", "ad12ec", "343c2");
        List as = Arrays.asList(strs);
        System.out.println(list.containsAll(Arrays.asList(strs)));
    }
    public static void main(String args[]) throws Exception{
        //处理客户信息
        String customer = "d:/客户BOM.xlsx";
        Workbook customerBook = ExcelUtils.getWorkbook(customer);
        List<String> customerValue = getCustomerValue(customerBook);
        LinkedHashMap processedCustomerValue = processCustomerValue(customerValue);
//        System.out.println(JSON.toJSONString(processedCustomerValue));
        //处理公司信息

        String company = "d:/★尼得科商用SUB ASSY 1HP POOL PUMP WW MOTOR CONTROL主控板OEM--HGWN-373DA(高银).xlsx";
        Workbook companyBook = ExcelUtils.getWorkbook(company);
        List<CompanyInfo> companyInfos = resolveCompanyValue(companyBook);
//        System.out.println(JSON.toJSONString(companyInfos));
        List<CompanyInfo> infos = sort(processedCustomerValue, companyInfos);
//        System.out.println(JSON.toJSONString(infos));
//        for(CompanyInfo info : infos){
//            if(StringUtils.isNotBlank(info.getPositionNum()))
//                System.out.println(info.getPositionNum());
//            else{
//                System.out.println(JSON.toJSONString(info));
//            }
//        }
        String name = company.substring(company.lastIndexOf("/") + 1, company.lastIndexOf(".")) + "副本";
        Workbook workbook = ExportExcelHandler.exportDateToExcel(name, infos, ExportExcelHandler.Type.XLSX);
        workbook.write(new FileOutputStream(new File("D:/" + name + ExportExcelHandler.Type.XLS.getExpandedName())));

    }



    private static List getCustomerValue(Workbook workbook){

        Sheet sheet = workbook.getSheetAt(1);
        ArrayList<String> list = new ArrayList<>(sheet.getLastRowNum());
        for(int i = 4, total = sheet.getLastRowNum(); i < total; i++ ){
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(3);
            try{
//                System.out.println(cell.getStringCellValue());
                String value = cell.getStringCellValue();
                if(StringUtils.isNotBlank(value))
                    list.add(cell.getStringCellValue());
            }catch(Exception ex){
//                System.out.println(ex.getMessage());
            }

        }
        return list;
    }

    private static LinkedHashMap processCustomerValue(List<String> list){
        LinkedHashMap<String, List<String>> result = Maps.newLinkedHashMap();
        for(String str : list){
            String [] strs = str.split(SPIT);
            List<String> one = Lists.newArrayList();
            one.addAll(Arrays.asList(strs));
            result.put(str, one);
        }
        return  result;
    }
    private static List resolveCompanyValue(Workbook workbook){
        List<CompanyInfo> list = Lists.newArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        for(int i = 1, total = sheet.getLastRowNum(); i < total; i ++){
            Row row = sheet.getRow(i);
            CompanyInfo one = new CompanyInfo();
            int cellNum = 0;
            one.setProject(row.getCell(cellNum++).getStringCellValue());
            one.setLevel(row.getCell(cellNum++).getStringCellValue());
            one.setBomVersion(row.getCell(cellNum++).getStringCellValue());
            one.setChildMaterialCode(row.getCell(cellNum++).getStringCellValue());
            one.setMaterialName(row.getCell(cellNum++).getStringCellValue());
            one.setMode(row.getCell(cellNum++).getStringCellValue());
            one.setMPNMFG(row.getCell(cellNum++).getStringCellValue());
            one.setMaterialProperty(row.getCell(cellNum++).getStringCellValue());
            one.setLT(row.getCell(cellNum++).getStringCellValue());
            one.setMOQ(row.getCell(cellNum++).getStringCellValue());
            one.setUnit(row.getCell(cellNum++).getStringCellValue());
            one.setUseNum(row.getCell(cellNum++).getStringCellValue());
            one.setUseAble(row.getCell(cellNum++).getStringCellValue());
            one.setUseState(row.getCell(cellNum++).getStringCellValue());
            one.setChildProject(row.getCell(cellNum++).getStringCellValue());
            one.setLevelClass(row.getCell(cellNum++).getStringCellValue());
            one.setPositionNum(row.getCell(cellNum++).getStringCellValue());
            one.setStation(row.getCell(cellNum++).getStringCellValue());
            one.setRemarke(row.getCell(cellNum++).getStringCellValue());
            one.setMainMateiral(row.getCell(cellNum++).getStringCellValue());
            list.add(one);
        }
        return list;
    }

    private static List sort(Map<String, List<String>> map, List<CompanyInfo> infos){
        List<CompanyInfo> result = Lists.newArrayList();
        for (Map.Entry<String, List<String>> entry : map.entrySet()){//按照客户的顺序来
            String customerStr = entry.getKey();
            List<String> customerSeq = entry.getValue();
            for(int i = 0;  i < infos.size(); i++){//找出公司信息中所有相同位置号的元素
                CompanyInfo info = infos.get(i);
                String position = info.getPositionNum();
                String [] positionNums = position.split(SPIT);
                if(!customerSeq.containsAll(Arrays.asList(positionNums))) continue;
                info.setPositionNum(customerStr);
                result.add(info);
                infos.remove(info);
            }
        }
        result.add(new CompanyInfo());//分隔行
        //把剩下的客户没有位置号的加到中间
        for(int i = 0; i < infos.size(); i++){
            CompanyInfo info = infos.get(i);
            if(StringUtils.isNotBlank(info.getPositionNum())){
                result.add(info);
                infos.remove(info);
            }
        }
        result.add(new CompanyInfo());
        //把没有位置号的加到末尾
        for(CompanyInfo info : infos){
            result.add(info);

        }
        return result;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    private static class CompanyInfo{
        @ExportExcel(order = 1, name = "项次")
        private String project;
        @ExportExcel(order = 2, name = "层次")
        private String level;
        @ExportExcel(order = 3, name = "BOM版本号")
        private String bomVersion;
        @ExportExcel(order = 4, name = "子项物料代码")
        private String childMaterialCode;
        @ExportExcel(order = 5, name = "物料名称")
        private String materialName;
        @ExportExcel(order = 6, name = "规格型号")
        private String mode;
        @ExportExcel(order = 7, name = "MPN(MFG)")
        private String MPNMFG;
        @ExportExcel(order = 8, name = "物料属性")
        private String materialProperty;
        @ExportExcel(order = 9, name = "LT")
        private String LT;
        @ExportExcel(order = 10, name = "MOQ")
        private String MOQ;
        @ExportExcel(order = 11, name = "单位")
        private String unit;
        @ExportExcel(order = 12, name = "用量")
        private String useNum;
        @ExportExcel(order = 13, name = "是否禁用")
        private String useAble;
        @ExportExcel(order = 14, name = "使用状态")
        private String useState;
        @ExportExcel(order = 15, name = "子项次")
        private String childProject;
        @ExportExcel(order = 16, name = "行类别")
        private String levelClass;
        @ExportExcel(order = 17, name = "位置号")
        private String positionNum;
        @ExportExcel(order = 18, name = "工位")
        private String station;
        @ExportExcel(order = 19, name = "行备注")
        private String remarke;
        @ExportExcel(order = 20, name = "主料")
        private String mainMateiral;


    }
}
