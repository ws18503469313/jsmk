package com.itmuch.model.wx;
/**
 * 模板消息的一条具体内容
 * @author polunzi
 *
 */
public class Content {
	
	private String 	value;
	
	private String color;
	
	
	public Content() {
		super();
	}

	public Content(String value, String color) {
		super();
		this.value = value;
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
