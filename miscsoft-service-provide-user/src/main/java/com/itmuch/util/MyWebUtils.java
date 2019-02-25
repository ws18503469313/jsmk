package com.itmuch.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyWebUtils {
	
	public static String getCtx(){
	    HttpServletRequest request = getCurrentRequest();
	    return (String)request.getSession().getAttribute("ctx");
	}
	
	public static HttpServletRequest getCurrentRequest() {
	    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	    if ((requestAttributes instanceof ServletRequestAttributes)) {
	      HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
	      return request;
	    }
	    return null;
	}
	
	public static boolean isAjax(HttpServletRequest httpRequest) {
		return (httpRequest.getHeader("X-Requested-With") != null
				&& "XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With").toString()));
	}
}
