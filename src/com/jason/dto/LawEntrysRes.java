package com.jason.dto;

import java.util.List;

public class LawEntrysRes {
	private List<LawEntry> lelist;
	private int total;
	private long costTime;
	
	public long getCostTime() {
		return costTime;
	}
	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}
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
