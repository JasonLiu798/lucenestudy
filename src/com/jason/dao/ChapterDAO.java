package com.jason.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.jason.dto.Chapter;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;

@Repository
public class ChapterDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		          this.sessionFactory = sessionFactory;
	}
	
	public ChapterDAO() {
	}
	
	public Chapter getChapterById(int cid){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Chapter.class)
				.add( Restrictions.eq("cid", cid) );
		List<Chapter> res = crit.list();
		session.close();
		return res.get(0);
	}
	
	public List<Chapter> getChaptersByLid(int lid){
		Law law = new Law();
		law.setLid(lid);
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Chapter.class)
				.add( Restrictions.eq("law", law) );
		List<Chapter> res = crit.list();
		session.close();
		return res;
	}
	
	public void saveOne(Chapter instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();
		session.clear();
		session.close();
	}
	
	public void saveAll( List<Chapter> lws){
		int len = lws.size();
		for(int i=0;i<len;i++){
			saveOne(lws.get(i));
		}
	}
	
	public static void main(String[] args) {
		String path="applicationContext.xml";
		ApplicationContext factory = new ClassPathXmlApplicationContext(path);
		
		ChapterDAO cc  = (ChapterDAO)factory.getBean("chapterDao");
		
//		Chapter c = cc.getChapterById(2);
//		System.out.println(c);
		
		List<Chapter> lc = cc.getChaptersByLid(1);
		for(int i=0;i<lc.size();i++){
			Chapter c = lc.get(i);
			System.out.println(c);
		}
	}

}
