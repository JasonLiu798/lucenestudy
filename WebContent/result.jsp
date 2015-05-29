<%@page import="com.jason.dto.KeywordHistory"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.jason.dto.LawEntrysRes" %>
<%@ page import="com.jason.dto.LawEntry" %>
<%@ page import="com.jason.dto.Keyword" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="head.jsp"%>

<%
Map data = (Map)request.getAttribute("data");
LawEntrysRes les = (LawEntrysRes)data.get("res");
List<Keyword> keywords = (List<Keyword>) data.get("keywords");

%>
	
	
  <title>LawSearch</title>
</head>
  <body class="resbody">
  <%@ include file="searchform.jsp"%>
  
	<div class="res">
		<div >
		搜索“<% out.print( les.getSearchText() ); %>”共耗时<% out.print( les.getCostTime() ); %>毫秒
		<% 	
			if(keywords.size()>0){
				out.print("，其中");
				Iterator<Keyword> itKeyword = keywords.iterator();
				int i=0;
				int len = keywords.size();
				while(itKeyword.hasNext()){
					Keyword kw = itKeyword.next();
					
					if( i==len-1 ){
				%>
					“<% out.print( kw.getSearchWord() ); %>”热度为<% out.print( kw.getCount() ); %>
				<%}else{%>
					“<% out.print( kw.getSearchWord() ); %>”热度为<% out.print( kw.getCount() ); %>，
		<%		
					i++;
				}
			}
		}%>
		</div>
  <% 
  if(les!=null){
		List<LawEntry> lws = les.getLelist();//les.getLelist();	

  if(lws!=null && lws.size()>0 ){
  	Iterator<LawEntry> it = les.getLelist().iterator();
  	while(it.hasNext() ){
  		LawEntry le = it.next();
  	  %>
  	  <div class="chapterdiv">
  	  <h2><a href="law/<%out.print( le.getChapter().getLaw().getLid() ); %>.do"><% if( le.getChapter().getLaw().getLid()==1) out.print("环保法"); 
  	  		 else if(  le.getChapter().getLaw().getLid() ==2) out.print("安全法"); %></a>
  	  </h2>
  	  <h3>
  	  	<a href="chapter/<%out.print(le.getChapter().getCid()); %>.do"><%=le.getChapter().getCname() %></a>
  	  </h3>
  	  <h4><%=le.getEname() %>
  	  </h4>
  	  <p><%=le.getSearchRes() %></p>
  	  </div>
	 <% 
	}
  }else{
	  out.print("<h3>无结果&nbsp;%&gt;_&lt;%</h3>");
  }
  }else{
	  out.print("<h3>无结果&nbsp;%&gt;_&lt;%</h3>");
  }
  	 %>
    </div>	
    
<%@ include file="foot.jsp"%>
