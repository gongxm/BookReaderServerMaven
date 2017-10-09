<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" type="text/css" href="css/main.css">

<script type="text/javascript">
	function rulesManagement() {
		location.href = "${pageContext.request.contextPath}/action/showAllRules";
	}
	function collectManagement() {
		location.href = "${pageContext.request.contextPath}/action/collect";
	}
</script>

<style type="text/css">
</style>

</head>
<body>
	<h1>网站后台</h1>
	<table class="table_center">
		<tr>
			<td align="center">
				<table class="table_inner">
					<tr>
						<td class="inner_td" style="width: 100px"><input
							type="button" value="采集规则管理" onclick="rulesManagement()" /></td>
						<td class="inner_td" style="width: 100px"><input
							type="button" value="采集管理" onclick="collectManagement()" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>


	<div class="go_to_index">
		<a class="href_none" href="${pageContext.request.contextPath}/">回到主页</a>
	</div>
</body>
</html>
