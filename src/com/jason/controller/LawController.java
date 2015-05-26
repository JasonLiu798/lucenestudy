package com.jason.controller;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import com.jason.database.DBCPPoolManager;
import com.jason.database.DBUtil;
import com.jason.dto.Chapter;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;

public class LawController {
	private BasicDataSource dm;
	private ChapterController cc;
	
	public LawController(BasicDataSource dm) {
//		if (dm == null) {
//			dm = new BasicDataSource();
//			
////			dm.startService();
//		}else{
		this.dm = dm;
//		}
		if(cc == null){
			cc = new ChapterController(dm);
		}
	}
	
	private LawController() {
		if (dm == null) {
			dm = new BasicDataSource();
//			dm.startService();
		}
		if(cc == null){
			cc = new ChapterController(dm);
		}
	}
	
	public Law getLawById(int lid){
		Law l = new Law();
		if(lid == 1){
			//hbf
			l.setLid(lid);
			l.setLname("环保法");
		}else if(lid ==2){
			l.setLid(lid);
			l.setLname("安全法");
		}
		List<Chapter> lcs = cc.getChaptersByLid(lid);
		l.setLcs(lcs);
		
		return l;
	}
}
