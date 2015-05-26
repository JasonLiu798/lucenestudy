<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.jason.dto.LawEntrysRes" %>
<%@ page import="com.jason.dto.LawEntry" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="head.jsp"%>

<%
String error = request.getAttribute("error")==null?"未知错误!":(String) request.getAttribute("error") ;
%>
	
	
  <title>Error</title>
</head>
  <body class="resbody">
  <%@ include file="searchform.jsp"%>
  
	<div class="res">
	<h1>出错了%&gt;_&lt;%</h1>
	<p>
  		<%   out.print( error ); %>
</p>
    </div>	
    
<%@ include file="foot.jsp"%>
