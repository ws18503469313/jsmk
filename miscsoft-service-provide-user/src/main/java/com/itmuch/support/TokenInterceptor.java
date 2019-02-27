package com.itmuch.support;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	public static String SET_KEY = "TOKEN_SET";
	public static String FORM_NAME = "_token";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Token token = (Token) handlerMethod.getMethod().getAnnotation(Token.class);
		if (token == null) {
			return super.preHandle(request, response, handler);
		}
		if (token.save()) {
			Set set = getSet(request);
			String formToken = UUID.randomUUID().toString();
			set.add(formToken);
			request.setAttribute(FORM_NAME, formToken);
		}
		if (token.remove()) {
			Set set = getSet(request);
			String formToken = request.getParameter(FORM_NAME);
			if (StringUtils.isBlank(formToken)) {
				return false;
			}
			if (!set.remove(formToken)) {
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}

	private Set<String> getSet(HttpServletRequest request) {
		Set set = (Set) request.getSession().getAttribute(SET_KEY);
		if (set == null) {
			set = new HashSet();
			request.getSession().setAttribute(SET_KEY, set);
		}
		return set;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
}
