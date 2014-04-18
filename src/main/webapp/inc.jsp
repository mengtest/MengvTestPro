<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%String version = "20140319";%>

<%
Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
if(null != cookies)
{
	for(Cookie cookie : cookies)
	{
		cookieMap.put(cookie.getName(), cookie);
	}
}
String easyuiTheme = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
if(cookieMap.containsKey("easyuiTheme"))
{
	Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
	easyuiTheme = cookie.getValue();
}
%>

<script type="text/javascript">
var mydef = mydef || {};
mydef.path = '<%=path%>';
mydef.basepath = '<%=basePath%>';
mydef.version = '<%=version%>';
mydef.pixel_0 = '<%=path%>/style/images/pixel_0.gif'; //0像素的背景，一般用于占位
</script>

<%--引入my97日期时间控件 --%>
<script type="text/javascript" src="<%=path%>/jslib/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>

<%-- 引入ueditor控件 --%>
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = '<%=path%>/jslib/ueditor1_2_6_1-utf8-jsp/';</script>
<script src="<%=path%>/jslib/ueditor1_3_6-utf8-jsp/ueditor.config.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/jslib/ueditor1_3_6-utf8-jsp/ueditor.all.min.js" type="text/javascript" charset="utf-8"></script>

<%--引入Jquery 判断浏览器版本--%>
<%
String User_Agent = request.getHeader("User-Agent");
if(StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1
		&& StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1
		|| StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1
		|| StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)
{
	out.println("<script src='" + path + "/jslib/jquery-1.9.1.js' type='text/javascript' charset='utf-8'></script>");
}
else
{
	out.println("<script src='" + path + "/jslib/jquery-2.0.3.js' type='text/javascript' charset='utf-8'></script>");
}
%>

<%-- 引入jQuery扩展 --%>
<script src="<%=path%>/jslib/extJquery.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入Highcharts --%>
<script src="<%=path%>/jslib/Highcharts-3.0.10/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/jslib/Highcharts-3.0.10/js/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<%-- 引入Highcharts扩展 --%>
<script src="<%=path%>/jslib/extHighcharts.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入plupload --%>
<script type="text/javascript" src="<%=path%>/jslib/plupload-2.1.1/js/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=path%>/jslib/plupload-2.1.1/js/i18n/zh_CN.js"></script>

<%-- 引入Easyui--%>
<link id="easyuiTheme" rel="stylesheet" href="<%=path%>/jslib/jquery-easyui-1.3.5/themes/<%=easyuiTheme%>/easyui.css" type="text/css">
<!-- <link rel="stylesheet" href="jslib/jquery-easyui-1.3.5/themes/default/easyui.css" type="text/css"></link> -->
<link rel="stylesheet" href="<%=path%>/jslib/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="<%=path%>/jslib/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/jslib/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<%--引入EasyUI Portal 插件 --%>
<link rel="stylesheet" href="<%=path%>/jslib/jquery-easyui-portal/portal.css" type="text/css"></link>
<script type="text/javascript" src="<%=path%>/jslib/jquery-easyui-portal/jquery.portal.js"></script>

<%--引入easyui扩展 --%>
<script type="text/javascript" src="<%=path%>/jslib/extEasyUI.js?version=<%=version%>"  charset="utf-8"></script>

<%--扩展图标 --%>
<link rel="stylesheet" href="<%=path%>/style/extJqCss.css?version=<%=version%>" type="text/css"></link>
<%--自定义样式 --%>
<link rel="stylesheet" href="<%=path%>/style/extIcon.css?version=<%=version%>" type="text/css"></link>

<%--javascript扩展 --%>
<script type="text/javascript" src="<%=path%>/jslib/extJavascript.js?version=<%=version%>"  charset="utf-8"></script>
