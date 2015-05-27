package com.jason.dto;

import java.util.List;

public class LawEntrysRes {
	private List<LawEntry> lelist;
	private int total;
	public List<LawEntry> getLelist() {
		return lelist;
	}
	public void setLelist(List<LawEntry> lelist) {
		this.lelist = lelist;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
