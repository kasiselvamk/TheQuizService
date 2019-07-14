package com.thequiz.thequiz;

import java.util.List;

public class quizforDay {
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	 
	public List getAns() {
		return ans;
	}
	public void setAns(List ans) {
		this.ans = ans;
	}
	 
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	Integer id;
	String p1;
	String p2;
	String p3;
	String ansexp;

	public String getAnsexp() {
		return ansexp;
	}
	public void setAnsexp(String ansexp) {
		this.ansexp = ansexp;
	}
	List<String> opt;
	public List<String> getOpt() {
		return opt;
	}
	public void setOpt(List<String> opt) {
		this.opt = opt;
	}
	List<Boolean> ans;
	boolean anscodeblock;
	public boolean isAnscodeblock() {
		return anscodeblock;
	}
	public void setAnscodeblock(boolean anscodeblock) {
		this.anscodeblock = anscodeblock;
	}
	String date;
	
}
