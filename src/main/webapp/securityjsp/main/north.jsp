<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="meng.model.base.SessionInfo" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>

<script type="text/javascript">
	var lockWindowFun = function(){
		$.post(mydef.path + '/base/tuser!doNotNeedSessionAndSecurity_logout.td', function(result){
			$('#loginDialog').dialog('open');
		}, 'json');
	};
	
	var logoutFun = function(){
		$.post(mydef.path + '/base/tuser!doNotNeedSessionAndSecurity_logout.td', function(result){
			location.replace(mydef.path + '/index.jsp');
		}, 'json');
	};
	
	var showMyInfoFun = function(){
		var dialog = parent.mydef.modalDialog({
			title : '我的信息',
			url : mydef.path + '/securityjsp/main/userInfo.jsp'
		});
	};
</script>

<div id="sessionInfoDiv" style="position:absolute; right:10px; top:5px">
	<%
		if(sessionInfo != null){
			out.print(meng.util.StringUtil.formateString("欢迎您,{0}", sessionInfo.getTuser().getLoginname()));
		}
	%>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="mydef.changeTheme('default');" title="default">default</div>
	<div onclick="mydef.changeTheme('gray');" title="gray">gray</div>
	<div onclick="mydef.changeTheme('metro');" title="metro">metro</div>
	<div onclick="mydef.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="mydef.changeTheme('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="mydef.changeTheme('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="mydef.changeTheme('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="mydef.changeTheme('metro-green');" title="metro-green">metro-green</div>
	<div onclick="mydef.changeTheme('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="mydef.changeTheme('metro-red');" title="metro-red">metro-red</div>
</div>

<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-lock'" onclick="lockWindowFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>
