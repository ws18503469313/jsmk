package com.itmuch.support;

import javax.servlet.http.HttpServletRequest;

public class SystemContext {
	
	private static ThreadLocal<Page> page = new ThreadLocal<Page>();
	
	private static ThreadLocal<HttpServletRequest> req = new ThreadLocal<HttpServletRequest>();
	
	
//	public static Integer getPageSize() {
//		return page.get().getPageSize();
//	}
//	
//	public static String getSort() {
//		return page.get().getSort();
//	}
//	
//	public static Integer getPageIndex() {
//		return page.get().getPage();
//	}
	
	public static Page getPage() {
		return page.get();
	}
	/**
	 * 	设置分页大小
	 * @param pageSize
	 */
	public static void setPageSize(Integer pageSize) {
		if(page.get() == null) {
			Page p = new Page();
			p.setPageSize(pageSize);
			page.set(p);
		}else {
			page.get().setPageSize(pageSize);
		}
	}
	/**
	 * 	设置第几页
	 * @param num
	 */
	public static void setPageIndex(Integer num) {
		if(page.get() == null) {
			Page p = new Page();
			p.setPage(num);
			page.set(p);
		}else {
			page.get().setPage(num);
		}
	}
	
	/**
	 * 设置排序方式
	 * @param sort
	 */
	public static void setSort(String sort) {
		if(page.get() == null) {
			Page p = new Page();
			p.setSort(sort);
			page.set(p);
		}else {
			page.get().setSort(sort);
		}
	}
	
	public static void removePage() {
		page.remove();
	}
	
	
	public static HttpServletRequest getReq() {
		return req.get();
	}
	public static void setRequest(HttpServletRequest request) {  
	    req.set(request);  
	}  
	
	public static void removeHttpServletRequest(){
		req.remove();
	}
	
	
}
