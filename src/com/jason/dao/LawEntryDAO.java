package com.jason.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jason.database.DBUtil;
import com.jason.dto.Chapter;
import com.jason.dto.LawEntry;
import com.jason.dto.User;

public class LawEntryDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		          this.sessionFactory = sessionFactory;
	}

	
	public LawEntryDAO(){
	}
	
	
	public LawEntry getLawEntryById(int eid){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(LawEntry.class)
//				.createAlias("chapter", "ch")
//				.add( Restrictions.eq("ch.cid", "cid") )
				.add( Restrictions.eq("eid", eid) );
		List<LawEntry> res = crit.list();
		session.close();
		return res.get(0);
	}
	
	public List<LawEntry> getLawEntrysByCid(int cid){
		Session session = sessionFactory.openSession();
		Chapter ch = new Chapter();
		ch.setCid(cid);
		Criteria crit = session.createCriteria(LawEntry.class).add( Restrictions.eq("chapter", ch) );
		List<LawEntry> res = crit.list();
		session.close();
		return res;
	}
	
	public List<LawEntry> getLawEntrys(){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(LawEntry.class);
		List<LawEntry> res = crit.list();
		session.close();
		return res;
	}
	
	public void saveOne(LawEntry instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();
		session.clear();
		session.close();
	}
	
	public void saveAll( List<LawEntry> lws){
		int len = lws.size();
		for(int i=0;i<len;i++){
			saveOne(lws.get(i));
		}
	}
	
	public List<LawEntry> chapters2LawEntrys(List<Chapter> lcs){
		Iterator<Chapter> lc = lcs.iterator();
		List<LawEntry> res  =  new LinkedList<LawEntry>();
		while (lc.hasNext()) {
			Chapter tmpCp = lc.next();
			res.addAll(tmpCp.getLawEntrys());
		}
		return res;
	}
	
	
	public static void main(String[] args) {
		String path="applicationContext.xml";
		ApplicationContext factory = new ClassPathXmlApplicationContext(path);
		
		LawEntryDAO ud = (LawEntryDAO)factory.getBean("lawentryDao");
//		System.out.println( ud.getLawEntryById(1));
		List<LawEntry> lws =  ud.getLawEntrysByCid(1);
		Iterator<LawEntry> it = lws.iterator();
		while (it.hasNext()) {
			LawEntry le = it.next();
			System.out.println(le);
		}
		
		/*
		User user = new User();
		user.setIp("afdasdfsafs");
		user.setUid(ud.getNewId());
		ud.saveOne(user);
		
		
		LawEntry lw = lc.getLawEntryById(2);
		System.out.println(lw);
		
		List<LawEntry> lws = lc.getLawEntrysByCid(2);
		
		
		Iterator<LawEntry> it = lws.iterator();
		while (it.hasNext()) {
			LawEntry le = it.next();
			System.out.println(le);
		}*/
		
	}

}
