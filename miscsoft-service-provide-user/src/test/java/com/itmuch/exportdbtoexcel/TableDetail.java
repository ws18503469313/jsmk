package com.itmuch.exportdbtoexcel;
/**
 *	一张表的字段详情
 * @author polunzi
 *
 */
public class TableDetail {
	/**
	 * 字段名
	 */
	private String cloumnName;
	/**
	 * 字段类型
	 */
	private String colunmType;
	/**
	 * 是否是主键
	 */
	private String isPri;
	/**
	 * 可否为空
	 */
	private String nullAble;
	/**
	 * 备注
	 */
	private String comment;
	
	public String getCloumnName() {
		return cloumnName;
	}
	public void setCloumnName(String cloumnName) {
		this.cloumnName = cloumnName;
	}
	public String getColunmType() {
		return colunmType;
	}
	public void setColunmType(String colunmType) {
		this.colunmType = colunmType;
	}
	public String getIsPri() {
		return isPri;
	}
	public void setIsPri(String isPri) {
		this.isPri = isPri;
	}
	public String getNullAble() {
		return nullAble;
	}
	public void setNullAble(String nullAble) {
		this.nullAble = nullAble;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
