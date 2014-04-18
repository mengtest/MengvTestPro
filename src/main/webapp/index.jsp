<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="meng.model.base.SessionInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>SSHE权限管理</title>
<jsp:include page="inc.jsp"></jsp:include>

<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
	if(sessionInfo != null)
	{
		request.getRequestDispatcher("/securityjsp/main/main.jsp").forward(request, response);
	}
	else
	{
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
%>

</head>

<body>
</body>
</html>
