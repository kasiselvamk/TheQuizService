package com.thequiz.thequiz;

import java.util.List;

public class dataObj {
    public Integer getQ() {
		return q;
	}
	public void setQ(Integer q) {
		this.q = q;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public List<List<String>> getOpt() {
		return opt;
	}
	public void setOpt(List<List<String>> opt) {
		this.opt = opt;
	}
	Integer q;
    String p1;
    String p2;
    String p3;
    String qdate;
    Boolean codeblock = Boolean.FALSE;
    
    public Boolean isCodeBlock() {
		return codeblock;
	}
	public void setCodeBlock(Boolean codeBlock) {
		this.codeblock = codeBlock;
	}
	public String getQdate() {
		return qdate;
	}
	public void setQdate(String qdate) {
		this.qdate = qdate;
	}
	List<List<String>> opt;
}
