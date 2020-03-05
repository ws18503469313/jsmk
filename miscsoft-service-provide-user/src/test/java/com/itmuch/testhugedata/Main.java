package com.itmuch.testhugedata;

import com.cloud.util.excel.export.ExportExcelHandler;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //    1048576
    public static void main(String args[]) throws Exception {
        FileOutputStream out = null;
        try {
            long start = System.currentTimeMillis();
            List<Data> values = new ArrayList<>(200000);
            for (int i = 0; i < 200000; i++) {
                values.add(new Data());
            }
            System.out.println("创建list消耗时间: " + ((System.currentTimeMillis() - start ) / 1000));
            start = System.currentTimeMillis();
            Workbook workbook = ExportExcelHandler.exportDateToExcel("testData", values, ExportExcelHandler.Type.XLSX);
            System.out.println("创建 workbook 消耗时间: " + ((System.currentTimeMillis() - start ) / 1000));
            start = System.currentTimeMillis();
            out = new FileOutputStream(new File("F:/testData" + ExportExcelHandler.Type.XLSX.getExpandedName()));
            workbook.write(out);
            System.out.println("写文件消耗时间: " + ((System.currentTimeMillis() - start ) / 1000));
            start = System.currentTimeMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) out.close();

        }
    }
}
