package com.itmuch.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
@Deprecated
public class ExcelUtil {
	
	private static int times;
	/**
	 * 控制主方法
	 * @param strs
	 * @param cishu
	 * @param out 
	 * @throws Exception
	 */
	public static void convertTOTable(List<String> strs, int cishu, HttpServletResponse resp) throws Exception {
		times = cishu;
		Workbook workbook = new HSSFWorkbook();
		// 初始化style
		getStyle(workbook);
		// 写第一部分
		writeFirstPage(workbook, strs.size());
		// 时间转换
		Date[] datas = convertStringToDate(strs);
		// 写第二部分
		writeSecondPage(workbook, datas);
		// 开始着色区分电脑
		drawColr(workbook, strs.size());
		// 输出
		write(workbook, resp);

	}
	static SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 将输入的参数转换为时间后进行排序
	 * @param strs
	 * @return
	 * @throws ParseException
	 */
	private static Date[] convertStringToDate(List<String> strs) throws ParseException {
		Date[] datas = new Date[strs.size()];
		long [] timemills = new long[strs.size()];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int i = 0;
		for (String string : strs) {
			Date date = sdf.parse(string);
			
			timemills[i++] = date.getTime();
		}
		Arrays.sort(timemills);
		i = 0;
		for (long timemill : timemills) {
			datas[i++] = new Date(timemill);
		}
		return datas;
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	private static String convertDateToFullString(Date date) {
		return sdfFull.format(date);
	}
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	/**
	 * 将日期转换为String
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private static String convertDateToString(Date date) throws ParseException {
		return sdf.format(date);
	}
	/**
	 * 将String转换为日期
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	private static Date convertStringToDate(String str) throws ParseException {
		return sdf.parse(str);
	}

	/**
	 * 写第二页
	 * 
	 * @param workbook
	 * @throws Exception
	 */
	private static void writeSecondPage(Workbook workbook, Date[] datas) throws Exception {
		Sheet sheet = workbook.getSheetAt(0);
		int rownum = 4;
		for (int i = 0; i < times; i++) {
			Row row = sheet.createRow(rownum++);
			int column = 0;
			Cell cell = null;
			for (int j = 0; j < datas.length; j++) {
				cell = row.createCell(column++);
				cell.setCellValue(convertDateToString(datas[j]));
				datas[j] = new Date((datas[j].getTime() + 2 * 60 * 1000));
			}
		}
		// 设置列宽
		for (int i = 0; i < datas.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 35 / 10);
		}
	}

	/**
	 * 写第一页
	 * 
	 * @param workbook
	 */
	private static void writeFirstPage(Workbook workbook, int colums) {
		Sheet firstPage = workbook.createSheet("你好呀 ");
		Row title = firstPage.createRow(0);
		Cell cell = null;
		cell = title.createCell(0);
		cell.setCellValue("靳慧同学,你好呀 ! ^_^");
		// 从第0行到第一行,从第0列到第10列
		firstPage.addMergedRegion(new CellRangeAddress(0, 3, 0, colums-1));
		cell.setCellStyle(getStyle(workbook));

	}

	/**
	 * 获取一个style
	 * @return 
	 */
	private static CellStyle getStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font3 = workbook.createFont();// 设置字体
		font3.setFontHeightInPoints((short) 22);
		style.setFont(font3);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
		style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);// 前景填充色
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框
		return style;

	}
	/**
	 * 获取一个cellstyle
	 * @return
	 */
	private static CellStyle getCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font3 = workbook.createFont();// 设置字体
		font3.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font3);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框
		return cellStyle;
	}

	/**
	 * 输出到文件
	 * @param out2 
	 * 
	 * @param workbook
	 * @throws Exception
	 */
	private static void write(Workbook workbook,HttpServletResponse resp) throws Exception {
		try {
			String name = "采血点"+ExcelUtil.convertDateToFullString( new Date())+".xls";
            String fileName = new String(name.getBytes("gb2312"), "ISO8859-1");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("multipart/form-data");
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = resp.getOutputStream();
            // 把相应的Excel 工作簿存盘
            workbook.write(out);
            out.flush();
            // 操作结束，关闭文件
            out.close();
            System.out.println("文件生成...");

        } catch (Exception e) {
            System.out.println("已运行 xlCreate() : " + e);
        }
	}

	public static void drawHeart() {

	}
	/**
	 *	规划电脑
	 * @param columns
	 * @throws Exception
	 */
	private static void drawColr(Workbook workbook, int columns) throws Exception {
		Sheet sheet = workbook.getSheetAt(0);
		int firstRow = 4;
		int lastRow = 3 + times;
		Class<?>[] clzs = HSSFColor.class.getDeclaredClasses();
		List<Class<?>> inclued = new ArrayList<Class<?>>();
		Map<Class<?>, Cell> useing = new HashMap<Class<?>, Cell>();//某台电脑最后的使用时间
		for (Class<?> c : clzs) {
			inclued.add(c);
		}
		inclued.remove(HSSFColor.GREEN.class);
		inclued.remove(HSSFColor.BLACK.class);
		// 直接给第一列上色
		CellStyle style = getCellStyle(workbook);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
		style.setFillForegroundColor(HSSFColor.GREEN.index);// 前景填充色
		useing.put(HSSFColor.GREEN.class, sheet.getRow(lastRow).getCell(0));
		setColomColor(sheet, 0, style);
		// 开始给后面的上色
		for (int colum = 1; colum < columns; colum++) {
			// 获取当前列第一个时间点 this
			Date thisFirst = convertStringToDate(sheet.getRow(firstRow).getCell(colum).toString());
			boolean drawn = false;
			for (Class<?> clz : useing.keySet()) {
				Cell cell = useing.get(clz);
				String lastUsingTimeString = cell.getStringCellValue();
				Date lastUsingTime  = convertStringToDate(lastUsingTimeString);
				if(thisFirst.compareTo(lastUsingTime) ==1) {
					// 给当前列上色
					setColomColor(sheet, colum, cell.getCellStyle());
					//更新这台电脑最后的使用时间
					useing.put(clz, sheet.getRow(lastRow).getCell(colum));
					drawn = true;
					break;
				}
			}
			//如果没有还没有上色,则选一种新的颜色上色
			if (!drawn) {
				Random random = new Random();
				int n = random.nextInt(inclued.size());
				Class<?> clz = inclued.get(n);
				Field field = clz.getDeclaredField("index");
				CellStyle noUsedStyle = getCellStyle(workbook);
				noUsedStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
				noUsedStyle.setFillForegroundColor(field.getShort(clz));// 前景填充色
				inclued.remove(clz);
				setColomColor(sheet, colum, noUsedStyle);
				//更新这台电脑最后的使用时间
				useing.put(clz, sheet.getRow(lastRow).getCell(colum));
			}

		}
	}


	/**
	 * 对具体某一列着色
	 * 
	 * @param sheet
	 * @param column //第几列
	 * @param style
	 * @param times  需要有多少行着色
	 */
	private static void setColomColor(Sheet sheet, int column, CellStyle style) {
		Row row = null;
		for (int i = 4; i < times + 4; i++) {
			row = sheet.getRow(i);
			Cell cell = row.getCell(column);
			cell.setCellStyle(style);
		}
	}
}
