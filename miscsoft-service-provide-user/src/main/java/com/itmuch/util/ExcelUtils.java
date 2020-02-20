package com.itmuch.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelUtils {

    public static Workbook getWorkbook(String name) throws Exception {
        FileInputStream in = new FileInputStream(new File(name));
        Workbook workbook = null;
        if (name.endsWith("xls")) {
            workbook = new HSSFWorkbook(in);
        }
        if (name.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(OPCPackage.open(in));
        }
        return workbook;
    }
}
