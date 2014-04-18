<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="meng.model.base.SessionInfo" %>
<%@ page import="meng.model.base.Trole" %>
<%@ page import="meng.model.base.Torganization" %>
<%@ page import="meng.model.base.Tresource" %>
<%@ page import="meng.model.easyui.Tree" %>
<%@ page import="meng.util.DateUtil" %>
<%@ page import="meng.util.BeanUtils" %>
<%@ page import="meng.util.ConfigUtil" %>
<%@ page import="meng.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>

<%
	String contextPath = request.getContextPath();
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
	
	Set<Trole> roles = sessionInfo.getTuser().getTroles();//用户的角色
	Set<Torganization> organizations = sessionInfo.getTuser().getTorganizations();//用户的机构
	List<Tresource> resources = new ArrayList<Tresource>();//用户可访问的资源
	for (Trole role : roles) {
		resources.addAll(role.getTresources());
	}
	for (Torganization organization : organizations) {
		resources.addAll(organization.getTresources());
	}
	resources = new ArrayList<Tresource>(new HashSet<Tresource>(resources));//去重
	List<Tree> resourceTree = new ArrayList<Tree>();
	for (Tresource resource : resources) {
		Tree node = new Tree();
		BeanUtils.copyNotNullProperties(resource, node);
		node.setText(resource.getName());
		if (resource.getTresource() != null) {
			node.setPid(resource.getTresource().getId());
		}
		resourceTree.add(node);
	}
	String resourceTreeJson = com.alibaba.fastjson.JSON.toJSONString(resourceTree);
%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>

<%
	out.println("<script>var resourceTreeJson = '" + resourceTreeJson + "';</script>");
%>
<script type="text/javascript">
	$(function(){
		$('#resources').tree({
			parentField : 'pid',
			data : eval("(" + resourceTreeJson + ")")
		});
	});
</script>
</head>

<body class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'center', fit:true, border:false">
		<table style="width: 100%;">
			<tr>
				<td><fieldset>
					<legend>用户信息</legend>
					<table class="table" style="width: 100%">
						<tr>
							<th>用户ID</th>
							<td><%=sessionInfo.getTuser().getId()%></td>
							<th>用户IP</th>
							<td><%=sessionInfo.getTuser().getIp()%></td>
						</tr>
						<tr>
							<th>登录名</th>
							<td><%=sessionInfo.getTuser().getLoginname()%></td>
							<th>姓名</th>
							<td><%=sessionInfo.getTuser().getName()%></td>
						</tr>
						<tr>
							<th>性别</th>
							<td>
							<%
								if(sessionInfo.getTuser().getSex() != null && sessionInfo.getTuser().getSex().equals("1")){
									out.print("男");
								}else{
									out.print("女");
								}
							%>
							</td>
							<th>电话</th>
							<td><%=sessionInfo.getTuser().getPhoto()%></td>
						</tr>
						<tr>
							<th>创建时间</th>
							<td><%=DateUtil.dateToString(sessionInfo.getTuser().getCreatedatetime())%></td>
							<th>最后修改时间</th>
							<td><%=DateUtil.dateToString(sessionInfo.getTuser().getUpdatedatetime()) %></td>
						</tr>
					</table>
				</fieldset>
				</td>
			</tr>
			<tr>
				<td>
				 <fieldset>
				 	<legend>权限信息</legend>
				 	<table class="table" style="width: 100%">
				 		<thead>
				 			<tr>
				 				<th>角色</th>
				 				<th>机构</th>
				 				<th>权限</th>
				 			</tr>
				 		</thead>
				 		<tr>
				 			<td valign="top">
				 				<%
				 					out.println("<ul>");
				 					for(Trole role : roles){
				 						out.println(StringUtil.formateString("<li title='{1}'>{0}</li>", role.getName(), role.getDescription()));
				 					}
				 					out.println("</ul>");
				 				%>
				 			</td>
				 			<td valign="top">
				 				<%
				 					out.println("<ul>");
				 					for(Torganization organization : organizations){
				 						out.println(StringUtil.formateString("<li>{0}</li>", organization.getName()));
				 					}
				 					out.println("</ul>");
				 				%>
				 			</td>
				 			<td valign="top"><ul id="resources"></ul></td>
				 		</tr>
				 	</table>
				 </fieldset>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>

