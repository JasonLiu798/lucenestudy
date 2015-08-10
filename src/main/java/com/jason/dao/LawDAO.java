package com.jason.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.jason.database.DBCPPoolManager;
import com.jason.database.DBUtil;
import com.jason.dto.Chapter;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;
import com.jason.dto.User;

@Repository
public class LawDAO {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		          this.sessionFactory = sessionFactory;
	}
	

	public LawDAO() {
	}
	
	public Law getLawById(int lid){
		
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Law.class)
				.add( Restrictions.eq("lid", lid) );
		List<Law> res = crit.list();
		session.close();
		return res.get(0);
		
	}
	
	public static void main(String[] args) {
		String path="applicationContext.xml";
		ApplicationContext factory = new ClassPathXmlApplicationContext(path);
		
		LawDAO ldao  = (LawDAO)factory.getBean("lawDao");
		Law c = ldao.getLawById(1);
		System.out.println(c);
		
	}
	
	
	
}