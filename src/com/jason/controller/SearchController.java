package com.jason.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jason.dao.KeywordDAO;
import com.jason.dao.LawDAO;
import com.jason.dao.UserDAO;
import com.jason.database.DBCPPoolManager;
import com.jason.dto.Chapter;
import com.jason.dto.Keyword;
import com.jason.dto.KeywordHistory;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;
import com.jason.dto.LawEntrysRes;
import com.jason.dto.User;
import com.jason.lucene.LawSearcher;

public class SearchController implements Controller {
	
	private KeywordDAO wordDao;
	private UserDAO userDao;
	
	
	
	public KeywordDAO getWordDao() {
		return wordDao;
	}


	public void setWordDao(KeywordDAO wordDao) {
		this.wordDao = wordDao;
	}


	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		String err = "";
		ModelAndView mw = null;
		String text = request.getParameter("searchtext");
		
		String ip = getRemortIP(request);
		ip = ip+","+getIpAddr(request);
		
		if(text==null){
			err = "请输入搜索内容";
		}else{
			if(text.length()==0){
				err = "请输入搜索内容";
			}else if(text.length()>1024){
				err = "查询内容过长";
			}else{
				List<Keyword> kws = wordDao.getSepwordsAndSave(text );
				
				User newUser = userDao.saveNewUser(ip);
				userDao.saveSearchHistory(newUser, kws);
				
				LawSearcher sr = new LawSearcher();
				
				LawEntrysRes les = sr.searchContent( text, 1, 10);
				mw= new ModelAndView("/result", "les", les);
			}
		}
		
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		return mw;
	}
	
	
	public String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}  

	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
