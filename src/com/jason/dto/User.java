package com.jason.dto;

public class User {
	
	private int uid;
	private String ip;


	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Override
	public String toString() {
		return "User [uid=" + uid + ", ip=" + ip + "]";
	}
	
	
}
