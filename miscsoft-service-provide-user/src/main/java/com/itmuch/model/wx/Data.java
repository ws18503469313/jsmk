package com.itmuch.model.wx;
/**
 * wx推送模板消息展示的所有内容
 * @author polunzi
 *
 */
public class Data {

	private Content first;
	private Content keyword1;
	private Content keyword2;
	private Content keyword3;
	private Content remark;
	
	public Data() {
		super();
	}
	public Data(Content first, Content keyword1, Content keyword2, Content keyword3, Content remark) {
		super();
		this.first = first;
		this.keyword1 = keyword1;
		this.keyword2 = keyword2;
		this.keyword3 = keyword3;
		this.remark = remark;
	}
	public Content getFirst() {
		return first;
	}
	public void setFirst(Content first) {
		this.first = first;
	}
	public Content getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(Content keyword1) {
		this.keyword1 = keyword1;
	}
	public Content getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(Content keyword2) {
		this.keyword2 = keyword2;
	}
	public Content getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(Content keyword3) {
		this.keyword3 = keyword3;
	}
	public Content getRemark() {
		return remark;
	}
	public void setRemark(Content remark) {
		this.remark = remark;
	}
	
	
	
}
