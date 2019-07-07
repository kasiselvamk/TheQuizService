package com.thequiz.thequiz;

import java.util.List;

public class UserSubmitPayload {
	
	Integer hiddenq;
	String qdate;
	List<Boolean> ckBox;
	public Integer getHiddenq() {
		return hiddenq;
	}
	public void setHiddenq(Integer hiddenq) {
		this.hiddenq = hiddenq;
	}
	public String getQdate() {
		return qdate;
	}
	public void setQdate(String qdate) {
		this.qdate = qdate;
	}
	public List<Boolean> getCkBox() {
		return ckBox;
	}
	public void setCkBox(List<Boolean> ckBox) {
		this.ckBox = ckBox;
	}

}
