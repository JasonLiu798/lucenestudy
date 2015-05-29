package com.jason.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.jason.dao.KeywordDAO;
import com.jason.dao.UserDAO;
import com.jason.dto.Keyword;
import com.jason.dto.LawEntrysRes;
import com.jason.dto.User;
import com.jason.lucene.LawSearcher;
import com.jason.tools.NetTools;
import com.jason.tools.StringTools;





import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.mvc.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; 


@Controller
public class SearchController { //implements Controller {
	
	@RequestMapping("/recomand")
	@ResponseBody 
    public List<Keyword> recomand(@RequestParam("usertext") String usertext,  HttpSession session, HttpServletResponse response){//Map<String, Object> map) {
		usertext = StringTools.Iso2Utf8(usertext);
		System.out.println("recomand action "+usertext);
		
		List<Keyword> lk = wordDao.getRecomandKeyword(usertext);
		if(lk!=null){
			System.out.println("text "+usertext+" got "+lk.size()+" recomands");
			Iterator<Keyword> itr = lk.iterator();
			while(itr.hasNext() ){
				Keyword kw = itr.next();
				kw.setHistorys(null);
			}
		}
//		ModelAndView mav = new ModelAndView("jsonView");
//		mav.addObject(lk);
		//JSONArray jsonArr = JSONArray.fromObject(lk);
//		mw= new ModelAndView("/json", "json", jsonArr );
        return lk;
    }
	
	@RequestMapping("/search")  
    public ModelAndView result(Map<String, Object> map,@RequestParam("searchtext") String searchtext,HttpSession session,HttpServletRequest request, HttpServletResponse response ) {  
//		String isRecomand = request.getParameter("getrecomand");
//		String text =StringTools.Iso2Utf8 (request.getParameter("searchtext"));
		ModelAndView mw = null;
		
		String err = "";
		
		String ip = NetTools.getRemortIP(request);
		ip = ip+","+NetTools.getIpAddr(request);
		
		if(searchtext==null){
			err = "请输入搜索内容";
		}else{
			searchtext = searchtext.trim();
			if( searchtext.length()==0 ){
				err = "请输入搜索内容";
			}else if(searchtext.length()>1024){
				err = "查询内容过长";
			}else{
				List<Keyword> kws = wordDao.getSepwordsAndSave(searchtext );
				
				User newUser = userDao.saveNewUser(ip);
				userDao.saveSearchHistory(newUser, kws);
				System.out.println("IP:"+ip+",kw:"+kws.size());
				
				LawSearcher sr = new LawSearcher();
				LawEntrysRes les = sr.searchContent( searchtext, 1, 10);
				Map<String,Object> data = new HashMap<String,Object>();  
			    data.put("res", les);
			    data.put("keywords", kws );
			    
				mw= new ModelAndView("/result", "data", data );
			}
		}
		
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		return mw;//"/result";
    }
	
	
//	public ModelAndView handleRequest(HttpServletRequest request,
//			HttpServletResponse arg1) throws Exception {
//		
//	}
	
	
	 
	@Autowired
	private KeywordDAO wordDao;
	@Autowired
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
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
