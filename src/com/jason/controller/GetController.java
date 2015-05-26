package com.jason.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jason.database.DBCPPoolManager;
import com.jason.dto.Chapter;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;
import com.jason.controller.LawController;

public class GetController implements Controller {
	
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
		
		String idstr = request.getParameter("id");
		int id = -1;
		if(idstr!=null ){
			id = Integer.parseInt(idstr);
		}else{
			err = "参数错误";
		}
		
		String type = request.getParameter("type");
		
		System.out.println(type+","+ idstr );
		ModelAndView mw = null;
		if(type!=null){
			if( type.equals("law") ){
				LawController lc = new LawController(basicDataSource);
				Law law = lc.getLawById(id);
				
				mw= new ModelAndView("/law", "law", law);
			}else if( type.equals("chapter")){
				ChapterController cc = new ChapterController(basicDataSource);
				Chapter cp = cc.getChapterById(id);
				mw= new ModelAndView("/chapter", "cp", cp);
			}else if( type.equals("entry")){
				LawEntryController lc = new LawEntryController(basicDataSource);
				LawEntry le = lc.getLawEntryById(id);
				mw= new ModelAndView("/entry", "le", le);
			}else{
				err = "未知类型";
			}
		}else{
			err = "参数错误";
		}
		
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		
		return mw;
	}
	
}
