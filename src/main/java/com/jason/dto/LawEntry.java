package com.jason.dto;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;

public class LawEntry {
	
	private int eid;
	private String ename;
	private String content;
	private String searchRes;
	private Chapter chapter;
	
	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	
	public String getSearchRes() {
		return searchRes;
	}

	public void setSearchRes(String searchRes) {
		this.searchRes = searchRes;
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
		return "LawEntry (eid=" + eid + ", ename=" + ename + ", content=" + content +"[chapter:cid:"+ chapter.getCid()+ ",cname:"+chapter.getCname() +"])";
	}
	
	public Document createDoc()  {  
        Document doc = new Document();  
        //就像有某个商品，查询结果列表要展示商品的名称，ID，和跳转链接地址，所以从数据库取出name,id,url字段  
        doc.add(new StringField("lid", this.chapter.getLaw().getLid() +""  ,  Store.YES) );
        doc.add(new StringField("eid", this.eid+""  ,  Store.YES) );
        doc.add(new StringField("cid", this.chapter.getCid() +""  ,  Store.YES) );
        
        doc.add(new TextField("cname", this.chapter.getCname() , Store.YES));
        doc.add(new TextField("ename", this.ename , Store.YES));
        doc.add(new TextField("content", this.content , Store.YES));
        
        
        return doc;  
    }
	
	
	
}
