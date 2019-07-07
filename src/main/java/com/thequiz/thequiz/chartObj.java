package com.thequiz.thequiz;

import java.util.Arrays;
import java.util.List;

public class chartObj {
	
	public chartObj(Integer i,Integer...lArr) {
     	this.setQ(i);
    	this.setVal(Arrays.asList(lArr));
	}
	
	Integer q;
    public Integer getQ() {
		return q;
	}
	public void setQ(Integer q) {
		this.q = q;
	}
	public List getVal() {
		return val;
	}
	public void setVal(List val) {
		this.val = val;
	}
	List val;
}
