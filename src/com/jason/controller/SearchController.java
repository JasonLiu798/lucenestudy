package com.jason.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jason.database.DBCPPoolManager;
import com.jason.dto.Chapter;
import com.jason.dto.Keyword;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;
import com.jason.dto.LawEntrysRes;
import com.jason.dto.User;
import com.jason.lucene.LawSearcher;
import com.jason.controller.LawController;

public class SearchController implements Controller {
	
	private BasicDataSource basicDataSource;
	
	public BasicDataSource getBasicDataSource() {
        return basicDataSource;
    }

    public void setBasicDataSource(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
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
			}else{
				KeywordController kc = new KeywordController(basicDataSource);
				Keyword kw = new Keyword();
				kw.setKeyword(text);
				int kid = kc.getNewId();
				kw.setKid(  kid );
				kc.saveOne(kw);
				
				UserController uc = new UserController(basicDataSource);
				User user = new User();
				user.setIp( ip);
				user.setKid(kid  );
				uc.saveOne(user);
				
				LawSearcher sr = new LawSearcher();
				
				LawEntrysRes les = sr.searchPost( text, 1, 10);
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
	
}
