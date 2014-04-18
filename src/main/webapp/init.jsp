<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>数据库初始化</title>
<jsp:include page="inc.jsp"></jsp:include>

<script type="text/javascript">
	$(function() {
		$.post(mydef.path + '/init!doNotNeedSessionAndSecurity_initDb.td', function(result) {
			if (result.success) {
				window.location.replace(mydef.path + '/index.jsp');
			}
		}, 'json');
	});
</script>

</head>

<body>数据库初始化中，请稍候....
</body>
</html>
