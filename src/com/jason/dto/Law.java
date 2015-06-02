package com.jason.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Law {
	private int lid;
	private String lname;
	private Set<Chapter> chapters;
	
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
	public Set<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}
	@Override
	public String toString() {
		String res =  "Law {lid=" + lid + ", lname=" + lname + "\n(chapters:";
		if(chapters!=null){
			Iterator it =  chapters.iterator();
			while(it.hasNext() ){
				Chapter tmpCp = (Chapter) it.next();
				res+=""+tmpCp+"\n";
			}
		}
		res += ")}\n";
		return res;
	}
	
	
}
