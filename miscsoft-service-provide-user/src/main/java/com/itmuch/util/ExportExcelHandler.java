package com.itmuch.util;

import com.google.common.collect.Lists;
import com.itmuch.model.User;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ExportExcelHandler<T> {

    private static CellStyle style;

    public static <T> Workbook exportDateToExcel(String filename, List<T> datas, Type format) throws Exception {
        if(CollectionUtils.isEmpty(datas)) return null;
        T one = datas.get(0);
        if(StringUtils.isEmpty(filename)){
            filename = one.getClass().getName();
            filename = filename.substring(filename.lastIndexOf("."), filename.length());
        }
//        filename = filename + sdf.format(new Date()) + format.getExpandedName();



        Workbook workbook = new HSSFWorkbook();
        createStyle(workbook);

        Sheet sheet = workbook.createSheet(filename);

        List<Field> fields = getHeads(one);

        createHeader(sheet, fields);

        fillDatas(sheet, datas, fields);

        return workbook;

    }

    /**
     * 初始化一个字体
     * @param workbook
     */
    private static void createStyle(Workbook workbook){
        style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//  垂直居中
        Font font = workbook.createFont();//设置字体
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
    }

    /**
     * 获取进行过排序的要导出的字段
     * @param t
     * @return
     */
    private static <T> List<Field> getHeads(T t){

        Class cls  = t.getClass();
        List<Field> fields = Lists.newArrayList();
        Field [] annoedFields = cls.getDeclaredFields();
        for(Field field : annoedFields){
            if(!field.isAccessible()) field.setAccessible(Boolean.TRUE);
            if(field.isAnnotationPresent(ExportExcel.class)) fields.add(field);
        }
        Collections.sort(fields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                int o1order = o1.getAnnotation(ExportExcel.class).order();
                int o2order = o2.getAnnotation(ExportExcel.class).order();
                return  o1order > o2order ? 1 : o1order == o2order ? 0 : -1;
            }
        });
        return fields;
    }

    /**
     * 创建首行
     * @param sheet
     * @param heads
     * @param <T>
     */
    private static <T> void createHeader(Sheet sheet, List<Field> heads){
        Row row = sheet.createRow(0);
        Cell cell  = null;
        int i = 0;
        for(Field field: heads){
            cell = row.createCell(i ++);
            cell.setCellValue(field.getAnnotation(ExportExcel.class).name());
            cell.setCellStyle(style);
            sheet.setColumnWidth(i,5000);
        }
    }

    private static <T> void fillDatas(Sheet sheet, List<T> datas, List<Field> fields) throws Exception {
        Row row = null;
        Cell cell = null;
        for(T t: datas){
            row = sheet.createRow(sheet.getLastRowNum() + 1);
            int i = 0;
            for(Field field : fields){
                cell = row.createCell(i++);
                Object obj = field.get(t);
                cell.setCellValue(getRealCellValue(obj));
                cell.setCellStyle(style);
            }
        }
    }

    private static String getRealCellValue(Object obj){
        if(obj == null) return  "";
        if(obj instanceof String) return obj.toString();
        if(obj instanceof Byte) return ((Byte)obj).toString();
        if(obj instanceof Short) return ((Short)obj).toString();
        if(obj instanceof Integer) return ((Integer)obj).toString();
        if(obj instanceof Long) return ((Long)obj).toString();
        if(obj instanceof Float) return ((Float)obj).toString();
        if(obj instanceof Double) return ((Double)obj).toString();
        if(obj instanceof BigDecimal) return ((BigDecimal)obj).toString();
        if(obj instanceof Character) return ((Character)obj).toString();
        return obj.toString();
    }
    public static void main(String args[]) throws Exception{
//          User user = User(String id, String name, String username, Integer age, Double balance, Date birthday,
//                  String descript) {
        User u1 = new User("123", "name1", "username1", 12, 23213D, new Date(), "d1321");
        User u2 = new User("123", "name1", "username1", 12, 23213D, new Date(), "d1321");
        List<User> users = Lists.newArrayList();
        users.add(u1);
        users.add(u2);
        Workbook workbook = exportDateToExcel(null, users, Type.XLS);
        FileOutputStream out = new FileOutputStream("D:/test" + Type.XLS.getExpandedName());
        workbook.write(out);

    }
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public enum Type {

        XLS(".xls"),
        XLSX(".xlsx");

        private String expandedName;

        Type(String expandedName) {
            this.expandedName = expandedName;
        }

        public String getExpandedName() {
            return expandedName;
        }

        public static Type getByExpandedName(String expandedName) {
            Type type = null;
            if (StringUtils.isNotBlank(expandedName)) {
                for (Type t : Type.values()) {
                    if (StringUtils.equals(t.getExpandedName(), expandedName)) {
                        type = t;
                        break;
                    }
                }
            }
            return type;
        }
    }
}
