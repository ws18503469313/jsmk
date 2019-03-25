package com.itmuch.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.itmuch.model.Resource;
import com.itmuch.support.SystemContext;
/**
 *	获取前端传来的page信息,并存放到ThreadLocal中,
 *	不必mvc每层都传递,
 *	在BaseDAO中,直接获取
 * @author polunzi
 *	
 */
public class SystemContextFilter implements Filter {

	private static int pageSize;
	
	@Autowired
	private Resource resource;
	
	public void destroy() {

	}
	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			
			int pageIndex = 0;
			try {
				pageIndex = Integer.parseInt(req.getParameter("page"));
			} catch (NumberFormatException e) {
				pageIndex = 1;
			}
			SystemContext.setPageIndex(pageIndex);
			SystemContext.setPageSize(pageSize);
			SystemContext.setRequest((HttpServletRequest)req);
			chain.doFilter(req, resp);
		} finally {
			SystemContext.removePage();
		}
	}

	public void init(FilterConfig config) throws ServletException {
		try {
//			pageSize = Integer.parseInt(config.getInitParameter("pageSize"));
			pageSize = resource.getPagesize();
		} catch (NumberFormatException e) {
			pageSize = 5;
		}
	}

}