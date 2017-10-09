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
<title>编辑书籍信息规则</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/bookRules.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bookRules.js"></script>
</head>
<body>
	<h1>编辑书籍信息规则</h1>

	<div class="edit">
		<input type="button" value="选择数据源" id="edit" />
	</div>

	<form name="bookRulesForm" id="bookRulesForm">
		<table class="center_table">
			<caption>编辑书籍信息规则</caption>
			<tr>
				<td>ID</td>
				<td><input type="text" name="id" id="id"
					value="${bookRules.id}" readonly="readonly" /> <input
					type="hidden" name="book_list_rules_id" id="book_list_rules_id"
					value="${bookListRulesId}" /></td>
			</tr>
			<tr>
				<td>规则名称</td>
				<td><textarea rows="1" cols="20" name="rulesName"
						id="rulesName">${bookRules.rulesName}</textarea></td>
			</tr>
			<tr>
				<td>标题正则</td>
				<td><textarea rows="1" cols="20" name="titleRegex"
						id="titleRegex">${bookRules.titleRegex}</textarea></td>
			</tr>
			<tr>
				<td>作者正则</td>
				<td><textarea rows="1" cols="20" name="authorRegex"
						id="authorRegex">${bookRules.authorRegex}</textarea></td>
			</tr>
			<tr>
				<td>类别正则</td>
				<td><textarea rows="1" cols="20" name="categoryRegex"
						id="categoryRegex">${bookRules.categoryRegex}</textarea></td>
			</tr>
			<tr>
				<td>状态正则</td>
				<td><textarea rows="1" cols="20" name="statusRegex"
						id="statusRegex">${bookRules.statusRegex}</textarea></td>
			</tr>
			<tr>
				<td>封面正则</td>
				<td><textarea rows="1" cols="20" name="coverRegex"
						id="coverRegex">${bookRules.coverRegex}</textarea></td>
			</tr>
			<tr>
				<td>简介正则</td>
				<td><textarea rows="1" cols="20" name="shortIntroduceRegex"
						id="shortIntroduceRegex">${bookRules.shortIntroduceRegex}</textarea>
				</td>
			</tr>
			<tr>
				<td>目录内容区域正则</td>
				<td><textarea rows="1" cols="20" name="contentDivClass"
						id="contentDivClass">${bookRules.contentDivClass}</textarea></td>
			</tr>
			<tr>
				<td>正文内容区域正则</td>
				<td><textarea rows="1" cols="20" name="contentDivRegex"
						id="contentDivRegex">${bookRules.contentDivRegex}</textarea></td>
			</tr>

			<tr>
				<td colspan="2" class="center"><input type="submit" value="修改" />
					<input type="button" value="返回" id="cancel" /></td>
			</tr>
		</table>
	</form>





	<!-- 遮盖层 -->
	<div id="over" class="over"></div>

	<!-- 弹出窗口代码 -->
	<div id="floatdiv" style="height: 300px">
		<input class="close" id="close" type="button" value="关闭" />
		<h2>选择数据源</h2>
		<table class="table_inner table_margin"
			style='margin: 0px auto;width:300px;' id='datasource'>

		</table>
	</div>
</body>
</html>