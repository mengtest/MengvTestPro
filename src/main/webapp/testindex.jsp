<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script type="text/javascript" src="jslib/jQuery-2.1.0.js"></script>
  
  </head>
  
  <body>
    This is my JSP page. <br>
    <form name="test" action="${pageContext.request.contextPath}/base/tuser!grantRole.td" method="post">
    <input type="button" value="提交" onclick="this.form.submit()">
    </form>
    <form name="test2" action="${pageContext.request.contextPath}/base/tresource!doNotNeedSecurity_getMainMenu.td" method="post">
    <input type="button" value="提交" onclick="this.form.submit()">
    </form>
  </body>
</html>
