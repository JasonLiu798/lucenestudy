package com.jason.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Chapter {
	
	private Law law;
	
	private int cid;
	private String cname;
	
	private Set<LawEntry> lawEntrys;
	
	
	
	public Law getLaw() {
		return law;
	}



	public void setLaw(Law law) {
		this.law = law;
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



	public Set<LawEntry> getLawEntrys() {
		return lawEntrys;
	}



	public void setLawEntrys(Set<LawEntry> lawEntrys) {
		this.lawEntrys = lawEntrys;
	}



	@Override
	public String toString() {
		String res = "Chapter {cid=" + cid + ", cname=" + cname +"[law:lid "+law.getLid()+",lname:"+law.getLname()+"\n(Lawentrys:";
		
		if(lawEntrys!=null){
			Iterator it =  lawEntrys.iterator();
			while(it.hasNext() ){
				LawEntry tmpCp = (LawEntry) it.next();
				res+=""+tmpCp+"\n";
			}
		}
		res += ")}\n";
		return res;//str.toString();
	}
	
}
