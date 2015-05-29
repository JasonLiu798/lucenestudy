<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONArray" %>
<%
JSONArray json =(JSONArray) request.getAttribute("json");
out.print(json);
%>