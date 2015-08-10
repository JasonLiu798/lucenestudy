package com.jason.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jason.dao.ChapterDAO;
import com.jason.dao.LawDAO;
import com.jason.dto.Chapter;
import com.jason.dto.Keyword;
import com.jason.dto.Law;

@Controller
public class GetController { //implements Controller {
	
	@RequestMapping("/law/{lid}")
    public ModelAndView law( @PathVariable("lid") String lid ){
		String err= "";
		ModelAndView  mw = null;
		System.out.println("Law "+lid );
		if(lid==null){
			err = "无效参数";
		}else{
			int iLid = Integer.parseInt(lid);
			if(iLid==1 || iLid == 2){
				Law law = lawDao.getLawById( iLid);
				mw= new ModelAndView("/law", "law", law);
			}else{
				err = "错误参数";
			}
		}
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		return mw;
	}
	
	@RequestMapping("/chapter/{cid}")
    public ModelAndView chapter( @PathVariable("cid") String cid ){
		String err= "";
		ModelAndView  mw = null;
		if(cid==null){
			err = "无效参数";
		}else{
			int iCid = Integer.parseInt(cid);
			if(iCid <0|| iCid>17){
				err = "错误参数";
			}else{
				Chapter cp = chapterDao.getChapterById(iCid);
				mw= new ModelAndView("/chapter", "cp", cp);
			}
		}
		if(err.length()>0){
			mw= new ModelAndView("/error", "error", err);
		}
		return mw;
	}
	
	
	@Autowired
	private LawDAO lawDao;
	@Autowired
	private ChapterDAO chapterDao;
	
    
	public LawDAO getLawDao() {
		return lawDao;
	}


	public void setLawDao(LawDAO lawDao) {
		this.lawDao = lawDao;
	}


	public ChapterDAO getChapterDao() {
		return chapterDao;
	}


	public void setChapterDao(ChapterDAO chapterDao) {
		this.chapterDao = chapterDao;
	}

	
	
}
