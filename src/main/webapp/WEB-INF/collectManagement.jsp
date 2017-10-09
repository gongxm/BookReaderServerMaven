<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = "https://" + request.getServerName() + path + "/";
%>
<title>采集管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/CollectManagement.js"></script>
</head>
<body>
	<h1>规则列表</h1>
	<table class="table_center">
		<tr>
			<td align="center">

				<table class="table_inner" style="width: 300px">
					<tr>
						<td class="inner_td" style="width: 120px">规则ID</td>
						<td class="inner_td" style="width: 120px">规则名称</td>
						<td class="inner_td" style="width: 500px" colspan="5">操作</td>
					</tr>

					<c:forEach items="${rulesList}" var="rules" varStatus="vs">
						<tr>
							<td class="inner_td">${rules.id}</td>
							<td class="inner_td">${rules.rulesName}</td>
							<td class="inner_td"><input type="button" value="采集所有"
								onclick="collectAll(this)"></td>
							<td class="inner_td"><input type="button" value="采集书籍列表"
								onclick="collectBookList(this)"></td>
							<td class="inner_td"><input type="button" value="采集章节列表"
								onclick="collectBookInfo(this)"></td>
							<td class="inner_td"><input type="button" value="采集章节内容"
								onclick="collectChapter(this)"></td>
							<td class="inner_td"><input type="button" value="更新"
								onclick="update(this)"></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" align="center"><a class="href_none"
							href="${pageContext.request.contextPath}/">回到主页</a> <a
							class="href_none" href="${pageContext.request.contextPath}/admin">后台管理</a></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>