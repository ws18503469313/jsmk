package com.itmuch.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * Aug 31, 2012
 *
 */
public class ContextFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(ContextFilter.class);
	@Override
    public void destroy() {
    }

	@Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException,
            ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
	    String ctxPath = (String) session.getAttribute("ctx");
	    if (ctxPath == null) {
	    	ctxPath = parseCtx(request.getRequestURL().toString());
	    	if (logger.isDebugEnabled()) {
	    		logger.debug("ctx path = " + ctxPath);
	    	}
	    	session.setAttribute("ctx", ctxPath);
	    }
	    
	    chain.doFilter(arg0, arg1);
    }

	@Override
    public void init(FilterConfig arg0) throws ServletException {
	    
    }

	private String parseCtx(String requestUrl) {
		String[] arr = requestUrl.split("/");
		return arr[0] + "//" + arr[2];
	}
	
}
