package com.jason.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.lucene.analysis.Analyzer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jason.dto.Keyword;
import com.jason.dto.KeywordHistory;
import com.jason.lucene.PackedAnalyzer;

public class KeywordDAO {
	
	private SessionFactory sessionFactory;
	private PackedAnalyzer analyzerDao;
	


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		          this.sessionFactory = sessionFactory;
	}
	
	
	public PackedAnalyzer getAnalyzerDao() {
		return analyzerDao;
	}


	public void setAnalyzerDao(PackedAnalyzer analyzerDao) {
		this.analyzerDao = analyzerDao;
	}


	public KeywordDAO() {
	}
	
	public List<Keyword> getRecomandKeyword(String text){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Keyword.class).add( Restrictions.like("searchWord", text+"%") );
		List<Keyword> res = crit.list();
		session.close();
		return res;
		
	}
		
	public Keyword isExistKeyword(String word){
		Keyword res = null;
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Keyword.class).add( Restrictions.eq("searchWord", word) );
		if( crit.list().size()>0 ){
			res = (Keyword) crit.list().get(0);
		}
		session.close();
		return res;
	}
	
	public void addCount(Keyword word){
		Session session = sessionFactory.openSession();
		word.setCount(word.getCount()+1);
		session.saveOrUpdate( word );
		session.flush();
		session.clear();
		session.close();
	}
	
	
	public List<Keyword> getSepwordsAndSave(String searchText){
		List<String> strs = analyzerDao.getWords(searchText );
		List<Keyword> res = null;
		if(strs.size()>0){
			res = new LinkedList<Keyword>();
			Iterator<String> itr = strs.iterator();
			while(itr.hasNext()){
				KeywordHistory newkh = new KeywordHistory();
				String word = itr.next();
				
				Keyword oldKey = isExistKeyword(word);
				Keyword kw = null;
				int kid = 0;
				if( oldKey == null ){
					//new keyword
					kw = new Keyword();
					kid = getNewId();
					kw.setKid( kid  );
					kw.setCount( 1 );
					kw.setSearchWord( word );
					saveOneKeyword(kw);
				}else{
					//change old keyword
//					kw = new Keyword();
//					kid = oldkh.getKid();
//					kw.setKid( kid );
//					kw.setCount( oldkh.getCount() );
//					kw.setSearchWord( word );
					kw = oldKey;
					addCount(oldKey);
				}
				newkh.setKeyword( kw  );
				newkh.setSearchTime( new Date() );
				res.add(kw);
				saveOneSearch(newkh);
			}
		}
		return res;
	}
	
	
	public void saveOneKeyword(Keyword instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();  
		session.clear();  
		session.close();
	}
	
	public void saveOneSearch(KeywordHistory instance) {
		Session session = sessionFactory.openSession();
		session.save( instance );
		session.flush();  
		session.clear();  
		session.close();
	}
	
	public void saveAllSearch( List<KeywordHistory> lws){
		int len = lws.size();
		for(int i=0;i<len;i++){
			saveOneSearch(lws.get(i));
		}
	}
	
	
	
	public synchronized int getNewId(){
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Keyword.class).setProjection(
				Property.forName( Keyword.getPk() ).max().as("maxid"));
		List res = crit.list();
		String newStr = "";
		if(res.get(0)!=null){
			newStr = res.get(0)+"";
		}else{
			newStr = "0";
		}
		session.close();
		return Integer.parseInt( newStr )+1;
		
	}
	

	public static void main(String[] args) {
		String path="applicationContext.xml";
		ApplicationContext factory = new ClassPathXmlApplicationContext(path);
		
		KeywordDAO kc  = (KeywordDAO)factory.getBean("keywordDao");
		
//		kc.getSepwordsAndSave("环境保护");
		List<Keyword> lk = kc.getRecomandKeyword("环");
		Iterator<Keyword> itr = lk.iterator();
		while(itr.hasNext() ){
			Keyword kw = itr.next();
			kw.setHistorys(null);			
		}
		
		JSONArray jsonArr = JSONArray.fromObject(lk);
		System.out.println(jsonArr);
		
//		
//		Iterator<Keyword> itr = lk.iterator();
//		while(itr.hasNext() ){
//			Keyword kw = itr.next();
//			System.out.println(kw);
//		}
		
//		Keyword kh = kc.isExistKeyword("环境");
//		System.out.println(kh);
		
//		System.out.println(kc.getNewId());
//		Keyword kw = new Keyword();
//		kw.setKid(kc.getNewId());
//		kw.setKeyword("asdfasdf");
//		kc.saveOne(kw);
//		
//		kw.setKid(kc.getNewId());
//		kw.setKeyword("SD卡雷锋精神考虑到");
//		kc.saveOne(kw);
	}

}
