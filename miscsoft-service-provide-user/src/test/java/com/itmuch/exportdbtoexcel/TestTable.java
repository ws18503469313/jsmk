//package com.itmuch.exportdbtoexcel;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//import com.itmuch.BaseTests;
//import com.itmuch.mapper.UserMapper;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.assertj.core.util.Lists;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.alibaba.fastjson.JSON;
//import com.hnluchuan.smartstoragev2.dao.ChangeListDAO;
//import com.hnluchuan.smartstoragev2.test.BaseTest;
///**
// *	将数据库字段导出到excel
// * @author polunzi
// *
// */
//public class TestTable extends BaseTests {
//
////	@Autowired
////	private ChangeListDAO changeListDAO;
//
//	private UserMapper userMapper;
//	/**
//	 *	数据库名称
//	 */
//	private static String dbName  = "local";
//	@Test
//	public void test() throws Exception {
//		//获取数据库中的所有表
//		List<Object> paras = Lists.newArrayList(dbName);
//		String selectTableNameSql = "select TABLE_NAME as tableName, TABLE_COMMENT AS comment from information_schema.tables where table_schema= ? ORDER BY table_name";
//		List<Table> tables = changeListDAO.findBySql(selectTableNameSql, paras.toArray(), null, Table.class);
//
//		System.out.println(JSON.toJSON(tables));
//		//创建一个excel
//		Workbook workbook = new HSSFWorkbook();
//		createSimpleGuide(workbook, tables);
//		StringBuilder selectTableDetailSql  = new StringBuilder();
//		selectTableDetailSql.append(" SELECT COLUMN_NAME as cloumnName,COLUMN_TYPE as  colunmType, COLUMN_COMMENT as comment, ");
//		selectTableDetailSql.append(" CASE COLUMN_KEY WHEN 'PRI' THEN 'TRUE' ELSE	'FALSE' END as isPri, ");
//		selectTableDetailSql.append(" CASE IS_NULLABLE WHEN 'YES' THEN	'TRUE' ELSE	'FALSE' END as nullAble ");
//		selectTableDetailSql.append(" FROM INFORMATION_SCHEMA. COLUMNS ");
//		selectTableDetailSql.append(" WHERE");
//		selectTableDetailSql.append(" TABLE_SCHEMA = ? ");
//		selectTableDetailSql.append(" AND TABLE_NAME = ? ");
//		//根据表明循环获取所有表的字段详细信息,
//		//并创建写入一个sheet
//		for (Table table : tables) {
//			List<Object> selectTableDetailSqlParas = Lists.newArrayList();
//			selectTableDetailSqlParas.add(dbName);
//			selectTableDetailSqlParas.add(table.getTableName());
//			List<TableDetail> details =
//					changeListDAO.findBySql(selectTableDetailSql.toString(), selectTableDetailSqlParas.toArray(), null, TableDetail.class);
//			creaSheet(workbook, table.getTableName(), details);
//		}
//		//将文件导出到本地
//		File file = new File("D:/dbtabels.xls");
//		OutputStream outputStream = new FileOutputStream(file);
//		workbook.write(outputStream);
//		outputStream.close();
//		System.out.println("done");
//	}
//	/**
//	 * 	创建一个所有表的sheet概览
//	 * @param workbook
//	 * @param tables
//	 */
//	private static void createSimpleGuide(Workbook workbook, List<Table> tables) {
//		Sheet sheet = workbook.createSheet("表清单");
//		Row headRow = sheet.createRow(0);
//
//		Cell cell = headRow.createCell(0);
//		cell.setCellValue("名称");
//		sheet.setColumnWidth(0, 7000);
//
//		cell = headRow.createCell(1);
//		cell.setCellValue("名称");
//		sheet.setColumnWidth(1, 7000);
//		Row detailRow = null;
//		int rowNum = 1;
//		for (Table  table : tables) {
//			detailRow = sheet.createRow(rowNum++);
//
//			cell = detailRow.createCell(0);
//			if(table.getTableName() != null) {
//				cell.setCellValue(table.getTableName());
//			}else {
//				cell.setCellValue("");
//			}
//
//
//			cell = detailRow.createCell(1);
//			if(table.getComment() != null) {
//				cell.setCellValue(table.getComment());
//			}else {
//				cell.setCellValue("");
//			}
//		}
//	}
//	/**
//	 * 	详情sheet表头
//	 */
//	private static final String[] heads = new String [] {"代码", "名称", "数据类型", "主要的", "强制", "注释"};
//	/**
//	 * 	将数据表的详情填充到sheet
//	 * @param workbook
//	 * @param tableName
//	 * @param details
//	 */
//	private static void creaSheet(Workbook workbook, String tableName, List<TableDetail> details) {
//		System.out.println("创建第" + workbook.getNumberOfSheets() + "张表======" + tableName);
//		Sheet sheet = workbook.createSheet(tableName);
//
//		Row headRow = sheet.createRow(0);
//		Cell cell = null;
//		for (int i = 0; i < heads.length; i++) {
//			cell = headRow.createCell(i);
//			cell.setCellValue(heads[i]);
//			sheet.setColumnWidth(i, 4000);
//		}
//
//		int rowNum = 1;
//		Row detailRow = null;
//		for (TableDetail detail : details) {
//			detailRow = sheet.createRow(rowNum++);
//			int column = 0;
//
//			cell = detailRow.createCell(column++);
//			if(detail.getCloumnName() != null) {
//				cell.setCellValue(detail.getCloumnName());
//			}else {
//				cell.setCellValue("");
//			}
//
//			cell = detailRow.createCell(column++);
//			if(detail.getCloumnName() != null) {
//				cell.setCellValue(detail.getCloumnName());
//			}else {
//				cell.setCellValue("");
//			}
//
//			cell = detailRow.createCell(column++);
//			if(detail.getColunmType() != null) {
//				cell.setCellValue(detail.getColunmType());
//			}else {
//				cell.setCellValue("");
//			}
//
//			cell = detailRow.createCell(column++);
//			if(detail.getIsPri() != null) {
//				cell.setCellValue(detail.getIsPri());
//			}else {
//				cell.setCellValue("");
//			}
//
//			cell = detailRow.createCell(column++);
//			if(detail.getNullAble() != null) {
//				cell.setCellValue(detail.getNullAble());
//			}else {
//				cell.setCellValue("");
//			}
//
//			cell = detailRow.createCell(column++);
//			if(detail.getComment() != null) {
//				cell.setCellValue(detail.getComment());
//			}else {
//				cell.setCellValue("");
//			}
//
//		}
//	}
//
//}
