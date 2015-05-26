<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.jason.dto.LawEntrysRes" %>
<%@ page import="com.jason.dto.LawEntry" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="head.jsp"%>

<%
LawEntrysRes les = (LawEntrysRes)request.getAttribute("les");


%>
	
	
  <title>LawSearch</title>
</head>
  <body class="resbody">
  <%@ include file="searchform.jsp"%>
  
	<div class="res">
  <% 
  if(les!=null){
		List<LawEntry> lws = les.getLelist();//les.getLelist();	

  if(lws!=null && lws.size()>0 ){
  	Iterator<LawEntry> it = les.getLelist().iterator();
  	while(it.hasNext() ){
  		LawEntry le = it.next();
  	  %>
  	  <div class="chapterdiv">
  	  <h2><a href="get.do?type=law&id=<%out.print(le.getLid()); %>"><% if(le.getLid()==1) out.print("环保法"); 
  	  		 else if(le.getLid()==2) out.print("安全法"); %></a>
  	  </h2>
  	  <h3>
  	  	<a href="get.do?type=chapter&id=<%out.print(le.getCid()); %>"><%=le.getCname() %></a>
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
