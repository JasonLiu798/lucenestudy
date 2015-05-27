<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.jason.dto.Law" %>
<%@ page import="com.jason.dto.LawEntry" %>
<%@ page import="com.jason.dto.Chapter" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="head.jsp"%>

<%
Chapter cp = (Chapter)request.getAttribute("cp");
%>
	
	
  <title>Chapter</title>
</head>
  <body class="resbody">
  
  <%@ include file="searchform.jsp" %>
<div class="res">
  	<h2><% out.print( cp.getCname() ); %> </h2>
  	<%
	  	Iterator<LawEntry> itle = cp.getLawEntrys().iterator();
	  	while(itle.hasNext() ){
	  		LawEntry le = itle.next();
  	%>
  		<div class="chapterdiv">	  
  		<h3><% out.print(le.getEname() ); %></h3>
  		<p><%  out.print(le.getContent() ); %></p>
  </div>	  
<%
		}
  	
%>
  
  </div>
	
<%@ include file="foot.jsp"%>
