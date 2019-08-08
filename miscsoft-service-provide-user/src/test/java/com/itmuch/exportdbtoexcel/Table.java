package com.itmuch.exportdbtoexcel;
/**
 *	表的详情
 * @author polunzi
 *
 */
public class Table {

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 备注
	 */
	private String comment;

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	
	
}
