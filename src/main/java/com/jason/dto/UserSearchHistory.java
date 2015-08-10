package com.jason.dto;

import java.util.Date;

public class UserSearchHistory {
	
	private int id;
	private int uid;
	private int kid;
	private Date searchTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public Date getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}
	
	
	
	
}
