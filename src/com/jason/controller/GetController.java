package com.jason.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jason.dao.LawDAO;
import com.jason.dto.Chapter;
import com.jason.dto.Law;

public class GetController implements Controller {
	
	private BasicDataSource basicDataSource;
	private LawDAO lawCtrl;
	private ChapterController chapterCtrl;
	
	public LawDAO getLawCtrl() {
		return lawCtrl;
	}

	public void setLawCtrl(LawDAO lawCtrl) {
		this.lawCtrl = lawCtrl;
	}

	public ChapterController getChapterCtrl() {
		return chapterCtrl;
	}

	public void setChapterCtrl(ChapterController chapterCtrl) {
		this.chapterCtrl = chapterCtrl;
	}

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
				
				Law law = lawCtrl.getLawById(id);
				
				mw= new ModelAndView("/law", "law", law);
			}else if( type.equals("chapter")){
//				ChapterController cc = new ChapterController(basicDataSource);
				Chapter cp = chapterCtrl.getChapterById(id);
				mw= new ModelAndView("/chapter", "cp", cp);
			}else{
				err = "未知类型";
			}
//			else if( type.equals("entry")){
//				LawEntryController lc = new LawEntryController(basicDataSource);
//				LawEntry le = lc.getLawEntryById(id);
//				mw= new ModelAndView("/entry", "le", le);
//			}
		}else{
			err = "参数错误";
		}
		
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		
		return mw;
	}
	
}
