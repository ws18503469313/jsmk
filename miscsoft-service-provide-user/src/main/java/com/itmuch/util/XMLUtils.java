package com.itmuch.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.itmuch.model.wx.WXTextMessage;
import com.thoughtworks.xstream.XStream;

public class XMLUtils {

	private static final Logger log = LoggerFactory.getLogger(XMLUtils.class);
	
	private static String ROO_ELEMENT = "xml";
	
	public static <T> T xmlToPojo(Class<?> cls, HttpServletRequest req) throws Exception {
		// 从req中获取结果,并通过dom4j解析
		SAXReader reader = new SAXReader();
		InputStream in = req.getInputStream();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> children = root.elements();
		// 将xml转换为pojo
		T result = (T) cls.newInstance();
		for (Field attr : WXTextMessage.class.getDeclaredFields()) {
			for (Element child : children) {
				if (child.getName().equals(attr.getName())) {
					Method m = result.getClass().getDeclaredMethod("set" + attr.getName(),
							new Class[] { String.class });
//					log.info(m.toString());
					m.invoke(result, child.getText());
				}
			}
		}
		in.close();
		log.info("o.o.o.o.O.dom4j解析结果-pojo:"+result.toString()+"--------------------------");
		return result;
	}

	public static Map<String , String> xmltoMap(HttpServletRequest req) throws Exception{
		SAXReader reader = new SAXReader();
		InputStream in = req.getInputStream();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> children = root.elements();
		
		Map<String , String> result = Maps.newHashMap();
		for (Element ele : children) {
			result.put(ele.getName(), ele.getText());
		}
		log.info("o.o.o.o.O.dom4j解析结果-map:"+result.toString()+"--------------------------");
		return result;
	}

	public static void main(String[] args) throws Exception {
		xmlToPojo(WXTextMessage.class, null);
	}

	/**
	 * 将pojo转换为xml
	 * 
	 * @param model
	 * @return
	 */
	public static <T> String pojoToXML(T model) {
		XStream stream = new XStream();
		stream.alias(ROO_ELEMENT, model.getClass());
		return stream.toXML(model);
	}
	
}
