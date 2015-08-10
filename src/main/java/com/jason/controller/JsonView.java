package com.jason.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.servlet.view.AbstractView;

public class JsonView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray jsonArr = JSONArray.fromObject(model);
		// JsonBuilder jb = new JsonBuilder();
		PrintWriter out = response.getWriter();
		out.print(jsonArr.toString());
	}
	

}
