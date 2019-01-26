package com.itmuch.model;

import com.github.pagehelper.Page;

public class BaseModel<T> {

	private Page<T> page;

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}
	
}
