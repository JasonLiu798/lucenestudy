package com.jason.dto;

public class LawEntry {
	
	private int eid;
	private String ename;
	private int cid;
	private String content;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	
	
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Override
	public String toString() {
		return "LawEntry (eid=" + eid + ", ename=" + ename + ", cid=" + cid
				+ ", content=" + content + ")";
	}
	
	
	
	
	
}
