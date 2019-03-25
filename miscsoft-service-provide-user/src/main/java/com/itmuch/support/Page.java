package com.itmuch.support;

public class Page {
	private Integer page = Integer.valueOf(1);
	private Integer pageSize = Integer.valueOf(20);
	private Integer totalPages = Integer.valueOf(1);
	private Integer totalRecords = Integer.valueOf(15);
	private String dir;
	private String sort;
	private String alias;
	private Integer rows;

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		if (getRows() != null) {
			return getRows();
		}
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalRecords() {
		return this.totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getDir() {
		return this.dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getRows() {
		return this.rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}