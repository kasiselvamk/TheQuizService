package com.thequiz.thequiz;

public class Resp {
	
	Integer q;
	String qdate;
	public Integer getQ() {
		return q;
	}
	public void setQ(Integer q) {
		this.q = q;
	}
	public String getQdate() {
		return qdate;
	}
	public void setQdate(String qdate) {
		this.qdate = qdate;
	}
	public boolean isCorw() {
		return corw;
	}
	public void setCorw(boolean corw) {
		this.corw = corw;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}
	boolean corw;
	String title;
	String ans;

}
