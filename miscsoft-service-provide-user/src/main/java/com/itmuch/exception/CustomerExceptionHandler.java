package com.itmuch.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.itmuch.util.MyWebUtils;
/**
 * 	<pre>
 *	 统一异常处理,
 * 	这种方式返回的结果对于前端来说也是一次成功的请求,也在success里面处理,但是这里的success.code == 300
 * 	而jsonresult里面默认的status==200
 * 	</pre>
 * @author 86185
 *
 */
@ControllerAdvice
public class CustomerExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomerExceptionHandler.class);

	public static final String IMOOC_ERROR_VIEW = "err";


	@ExceptionHandler(value = Exception.class)
	public Object errorHandler(HttpServletRequest reqest, HttpServletResponse response, Exception e) throws Exception {
//		e.printStackTrace();
		log.error("error_message---------------------" + e.getMessage());
		if (MyWebUtils.isAjax(reqest)) {

			JSONObject json = new JSONObject();
        	json.put("success", false);
        	json.put("msg", e.getMessage());
        	json.put("status", 300);
    		json.put("message", e.getMessage());
    		response.setContentType("application/json; charset=UTF-8");
    		response.getWriter().print(json.toJSONString());
    		return null;
		} else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("exception", e);
			mav.addObject("url", reqest.getRequestURL());
			mav.setViewName(IMOOC_ERROR_VIEW);
			return mav;
		}
	}

	
}
