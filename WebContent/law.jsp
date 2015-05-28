<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.jason.dto.Law" %>
<%@ page import="com.jason.dto.LawEntry" %>
<%@ page import="com.jason.dto.Chapter" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="head.jsp"%>

<%
Law law = (Law)request.getAttribute("law");
%>
	
	
  <title>Law</title>
</head>
  <body class="resbody">
  
   <%@ include file="searchform.jsp"%>
	<div class="res">
  <h1><% out.print( law.getLname() ); %></h1>
  
<% 
  	Iterator<Chapter> itc = law.getChapters().iterator();
  	while(itc.hasNext() ){
  		Chapter ch = itc.next();
%>
	<div class="chapterdiv">
  	<h2><% out.print( ch.getCname() ); %></h2>
  	<%
	  	Iterator<LawEntry> itle = ch.getLawEntrys().iterator();
	  	while(itle.hasNext() ){
	  		LawEntry le = itle.next();
  	%>	  
  		<h3><% out.print(le.getEname() ); %></h3>
  		<p><%  out.print(le.getContent() ); %></p>
  	 
<%
		}
	  	%>
	  	</div>
	  	<%
  	}
%>
  </div>
<%@ include file="foot.jsp"%>