package com.jason.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.jason.dto.User;

public class UserDAO  {//extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		          this.sessionFactory = sessionFactory;
	}
//	private BasicDataSource basicDataSource;
	
	
//	public BasicDataSource getBasicDataSource() {
//		return basicDataSource;
//	}

//	public void setBasicDataSource(BasicDataSource basicDataSource) {
//		this.basicDataSource = basicDataSource;
//	}

	public UserDAO(){
		
	}
	
//	public UserController(BasicDataSource dm){
//		this.basicDataSource = dm;
//	}
	
	
	public void saveOne(User instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();  
		session.clear();  
		session.close();
	}
	
	public List<User> getAll(){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class);
		List<User> res = crit.list();
		return res;
	}
	
	public synchronized int getNewId(){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class).setProjection(
				Property.forName("uid").max().as("maxid"));
		List res = crit.list();
		String newStr = "";
		if(res.get(0)!=null){
			newStr = res.get(0)+"";
		}else{
			newStr = "0";
		}
//		crit.setMaxResults(50);
//		List<User> res = crit.list();
		return Integer.parseInt( newStr )+1;
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
		user.setIp("afdasdfsafs");
		user.setUid(ud.getNewId());
		ud.saveOne(user);
		
		
//		for(int i=0;i<res.size();i++){
//			System.out.println(res.get(i));
//		}
		
		
		
//		ud.flush();
		
		
		
//		gi.GenerateAllIndex(Constant.IDX_DIR , (LawEntryController)factory.getBean("lawentryCtrl"));

	}

}
