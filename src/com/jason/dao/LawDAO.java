package com.jason.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;

import com.jason.controller.ChapterController;
import com.jason.database.DBCPPoolManager;
import com.jason.database.DBUtil;
import com.jason.dto.Chapter;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;


public class LawDAO {
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
	}
	//private BasicDataSource basicDataSource;
	private ChapterController chapterController;
	
	
	public ChapterController getChapterController() {
		return chapterController;
	}

	public void setChapterController(ChapterController chapterController) {
		this.chapterController = chapterController;
	}

	public LawDAO() {
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