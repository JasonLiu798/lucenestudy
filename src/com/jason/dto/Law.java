package com.jason.dto;

import java.util.List;

public class Law {
	private int lid;
	private String lname;
	private List<Chapter> lcs;
	
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public List<Chapter> getLcs() {
		return lcs;
	}
	public void setLcs(List<Chapter> lcs) {
		this.lcs = lcs;
	}
	
	
}
