package com.jason.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jason.dao.KeywordDAO;
import com.jason.dao.UserDAO;
import com.jason.dto.Keyword;
import com.jason.dto.LawEntrysRes;
import com.jason.dto.User;
import com.jason.lucene.LawSearcher;
import com.jason.tools.NetTools;
import com.jason.tools.StringTools;

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
		String isRecomand = request.getParameter("getrecomand");
		String text =StringTools.Iso2Utf8 (request.getParameter("searchtext"));
		ModelAndView mw = null;
		
		String err = "";
		
		if(isRecomand!=null){
			List<Keyword> lk = wordDao.getRecomandKeyword(text);
			System.out.println("text "+text+" got "+lk.size()+" recomands");
			Iterator<Keyword> itr = lk.iterator();
			while(itr.hasNext() ){
				Keyword kw = itr.next();
				kw.setHistorys(null);			
			}
			JSONArray jsonArr = JSONArray.fromObject(lk);
			mw= new ModelAndView("/json", "json", jsonArr );
		}else{
			String ip = NetTools.getRemortIP(request);
			ip = ip+","+NetTools.getIpAddr(request);
			
			if(text==null){
				err = "请输入搜索内容";
			}else{
				text = text.trim();
				if( text.length()==0 ){
					err = "请输入搜索内容";
				}else if(text.length()>1024){
					err = "查询内容过长";
				}else{
					List<Keyword> kws = wordDao.getSepwordsAndSave(text );
					
					User newUser = userDao.saveNewUser(ip);
					userDao.saveSearchHistory(newUser, kws);
					
					LawSearcher sr = new LawSearcher();
					
					LawEntrysRes les = sr.searchContent( text, 1, 10);
					Map<String,Object> data = new HashMap<String,Object>();  
				    data.put("res", les);
				    data.put("keywords", kws );
				    
					mw= new ModelAndView("/result", "data", data );
				}
			}
		}
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		return mw;
	}
	
	
	 
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
