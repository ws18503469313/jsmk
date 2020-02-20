package com.itmuch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itmuch.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PoiUtil {
    private static final Logger log = LoggerFactory.getLogger(PoiUtil.class);

    private static Workbook getWorkbook(String name) throws Exception {
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

    public static List<List<String>> read(File file) throws Exception {

        InputStream in = new FileInputStream(file);
        Workbook wb = null;
        String name = file.getName().toLowerCase();
        if (name.endsWith("xls")) {
            wb = new HSSFWorkbook(in);
        }
        if (name.endsWith("xlsx")) {
            wb = new XSSFWorkbook(OPCPackage.open(in));
        }
        List<List<String>> list = new ArrayList<List<String>>();
        Sheet sheet1 = wb.getSheetAt(0);
        int i = 1;
        int j = 1;
        try {

            int rowNum = sheet1.getLastRowNum();
            Row row = sheet1.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            // 正文内容应该从第二行开始,第一行为表头的标题
            for (i = 1; i <= rowNum; i++) {
                row = sheet1.getRow(i);
                j = 0;
                List<String> dataList = new ArrayList<String>();
                while (j < colNum) {
                    Cell cell = row.getCell(j);
                    String content = null;
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        content = cell.getStringCellValue();
                        if (content != null) {
                            content = StringUtils.trim(content);
                        }
                    }
                    dataList.add(content);
                    j++;
                }
                list.add(dataList);
            }
        } catch (Exception e) {
            log.error("第" + (i + 1) + "行，第" + (j + 1) + "列导入失败", e);
            throw new RuntimeException(e);
        }
        return list;
    }


    public static <T> List<T> read(Workbook wb, String sheetName, Map<String, String> argColMap, Class<T> clz) {
        List<T> result = new ArrayList();
        Sheet sheet;
        Row row;
        Cell cell;
        if (StringUtils.isBlank(sheetName)) {
            sheet = wb.getSheetAt(0);
        } else {
            sheet = wb.getSheet(sheetName);
        }
        int rowNum = sheet.getLastRowNum();
        Map<Integer, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : argColMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            char[] charArr = value.toUpperCase().toCharArray();
            int index = 0;
            for (int i = 0; i < charArr.length; i++) {
                char c = charArr[i];
                index = index * 26 + (c - 64);
            }
            map.put(index - 1, key);
        }
        for (int j = 1; j < rowNum + 1; j++) {
            row = sheet.getRow(j);
            if (null == row) {
                continue;
            }
            Map<String, Object> valueMap = new HashMap<>();
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                cell = row.getCell(key);
                valueMap.put(value, getCellValue(cell));
            }
            try {
                result.add(GsonUtils.parse(GsonUtils.toJson(valueMap), clz));
            } catch (Exception e) {
                throw new BizException(e.getMessage());
            }
        }
        return result;
    }

    public static <T> List<T> read(InputStream is, String sheetName, Map<String, String> argColMap, Class<T> clz) {
        Workbook wb;
        try {
            wb = new XSSFWorkbook(is);
        } catch (Exception e) {
            try {
                wb = new HSSFWorkbook(new POIFSFileSystem(is));
            } catch (Exception ex) {
                throw new BizException("read excel failed!" + ex.getMessage());
            }
        }
        return read(wb, sheetName, argColMap, clz);
    }

    private static Object getCellValue(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    short format = cell.getCellStyle().getDataFormat();
                    if (format == 14 || format == 31 || format == 57 || format == 58) {   //excel中的时间格式
                        double value = cell.getNumericCellValue();
                        return DateUtil.getJavaDate(value);
                    } else if (HSSFDateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式,对2014-02-02格式识别不出是日期格式
                        return cell.getDateCellValue();
                    } else { // 如果是纯数字  取得当前Cell的数值
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        return cell.getStringCellValue();
                    }
                case Cell.CELL_TYPE_FORMULA:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue().replaceAll("'", "").trim();
                case Cell.CELL_TYPE_BLANK:
                    return null;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
}
