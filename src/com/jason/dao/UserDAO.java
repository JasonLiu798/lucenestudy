package com.jason.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jason.dto.Keyword;
import com.jason.dto.KeywordHistory;
import com.jason.dto.User;
import com.jason.dto.UserSearchHistory;

public class UserDAO  {//extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		          this.sessionFactory = sessionFactory;
	}
	
	public UserDAO(){
		
	}
	
	
	public void saveOne(User instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();  
		session.clear();  
		session.close();
	}
	
	public void saveSearchHistory(User user,List<Keyword> ks){
		Iterator<Keyword> iter = ks.iterator();
		while( iter.hasNext() ){
			Keyword kw = iter.next();
			UserSearchHistory ush = new UserSearchHistory();
			ush.setKid( kw.getKid());
			ush.setSearchTime( new Date() );
			ush.setUid( user.getUid() );
			saveSearchHistory(ush);
		}
	}
	
	public void saveSearchHistory(UserSearchHistory instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();  
		session.clear();  
		session.close();
	}
	
	public User saveNewUser(String ip){
		User oldUser = isExistIp(ip);
		User newUser = new User();
		if(oldUser==null){
			newUser.setIp(ip);
			newUser.setUid( getNewId() );
			saveOne(newUser);
		}
		return newUser;
	}
	
	public User isExistIp(String ip){
		User res = null;
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class).add( Restrictions.eq("ip", ip) );
		if( crit.list().size()>0 ){
			res = (User) crit.list().get(0);
		}
		session.close();
		return res;
	}
	
	public synchronized int getNewId(){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class).setProjection(
				Property.forName( User.getPk() ).max().as("maxid"));
		List res = crit.list();
		String newStr = "";
		if(res.get(0)!=null){
			newStr = res.get(0)+"";
		}else{
			newStr = "0";
		}
		return Integer.parseInt( newStr )+1;
	}
	
	
	public List<User> getAll(){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class);
		List<User> res = crit.list();
		return res;
	}
	
	
	
//	public List<User> get
	
	/*
	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}*/
	
	
	/*
	public boolean saveOne(User user){
		Session session = this.getSession();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = this..getConnection();
			stmt = conn
					.prepareStatement("INSERT INTO user ( ip,kid)  VALUES(?,?)");
			stmt.setString(1, user.getIp() );
//			stmt.setInt(2, user.getKid() );
			res = stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	*/
	
	/*
	public boolean saveSearchKeyword(User user,Keyword kw){
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = basicDataSource.getConnection();
			stmt = conn
					.prepareStatement("INSERT INTO user_keyword ( uid,kid)  VALUES(?)");
			stmt.setString(1, user.getIp() );
			res = stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	*/
	
	public static void main(String[] args) {
		
		String path="applicationContext.xml";
		ApplicationContext factory = new ClassPathXmlApplicationContext(path);
		
		UserDAO ud = (UserDAO)factory.getBean("userDao");
		System.out.println( ud.getNewId());
		
		User user = new User();
		user.setIp("10.111111");
		user.setUid(ud.getNewId());
		ud.saveOne(user);
		
		KeywordDAO kc  = (KeywordDAO)factory.getBean("keywordDao");
		
		List<Keyword> kws = kc.getSepwordsAndSave("安全生产");
		
		
		ud.saveSearchHistory(user,  kws );
		
//		for(int i=0;i<res.size();i++){
//			System.out.println(res.get(i));
//		}
		
		
		
//		ud.flush();
		
		
		
//		gi.GenerateAllIndex(Constant.IDX_DIR , (LawEntryController)factory.getBean("lawentryCtrl"));

	}

}
