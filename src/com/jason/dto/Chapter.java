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
		String res = "Chapter {cid=" + cid + ", cname=" + cname +"}";
//		StringBuffer str = new StringBuffer();
//		str.append("Chapter {cid=" + cid + ", cname=" + cname +"}");
//		
//		str.append("LawEntrys=");
//		str.append("Chapter {cid=" + cid + ", cname=" + cname+",LawEntrys=[" );
		if(lawEntrys!=null){
			Iterator it =  lawEntrys.iterator();
			while(it.hasNext() ){
				LawEntry tmpCp = (LawEntry) it.next();
				res+=""+tmpCp;
//				str.append( tmpCp+"" );
////				System.out.println( tmpCp);
//				System.out.println(tmpCp);
			}
		}else{
//			str.append(" null");
		}
		return res;//str.toString();
	}
//	Chapter {cid=2, cname=第二章生产经营单位的安全生产保障}LawEntry (lid=1, eid=17, e
	
}
