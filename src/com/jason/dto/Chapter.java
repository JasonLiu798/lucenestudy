package com.jason.dto;

import java.util.Iterator;
import java.util.List;

public class Chapter {
	
	private int cid;
	private int lid;
	private String cname;
	
	private List<LawEntry> lawEntrys;
	
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public List<LawEntry> getLawEntrys() {
		return lawEntrys;
	}
	public void setLawEntrys(List<LawEntry> lawEntrys) {
		this.lawEntrys = lawEntrys;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(); 
		str.append("Chapter {cid=" + cid + ", cname=" + cname+",LawEntrys=[" );
		Iterator it = (Iterator) lawEntrys.iterator();
		while(it.hasNext()){
			LawEntry tmpCp = (LawEntry) it.next();
			str.append( tmpCp );
//			System.out.println(tmpCp);
		}
		str.append("]}");
		return str.toString();
	}
	
	
}
