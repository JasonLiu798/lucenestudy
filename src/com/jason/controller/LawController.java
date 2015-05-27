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
	
//	private BasicDataSource basicDataSource;
//	private LawEntryController lawentryController;
//	
	private BasicDataSource basicDataSource;
	private ChapterController chapterController;
	
	
//	public LawController(BasicDataSource dm) {
//		this.basicDataSource = dm;
//		if(chapterController == null){
//			chapterController = new ChapterController(dm);
//		}
//	}
	
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}

	public ChapterController getChapterController() {
		return chapterController;
	}

	public void setChapterController(ChapterController chapterController) {
		this.chapterController = chapterController;
	}

	public LawController() {
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
		List<Chapter> lcs = chapterController.getChaptersByLid(lid);
		l.setLcs(lcs);
		
		return l;
	}
}