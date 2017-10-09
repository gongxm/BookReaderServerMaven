<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户登陆</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="css/main.css">
<style type="text/css">
</style>
</head>
<body>
	<h1 align="center">用户登陆</h1>
	<table class="table_center">
		<tr>
			<td align="center">
				<form
					action="${pageContext.request.contextPath}/loginServlet"
					method="post">
					<table class="table_inner">
						<tr>
							<td style="width: 80px" align="center">用户名:<input type="text" name="username" /></td>
						</tr>
						<tr>
							<td style="width: 80px" align="center">密 码  :<input type="password" name="password" /></td>
						</tr>
						<tr>
							<td align="center"><input type="checkbox" name="remember" />记住我</td>
						</tr>
						<tr>
							<td align="center"><input type="reset" value="清空" />  <input type="submit" value="登陆" /></td>
						</tr>

						<tr>
							<td align="center"><a href="${pageContext.request.contextPath}"><font color="#00f">回到主页</font></a>  <a class="href_none" href="${pageContext.request.contextPath}/regist.jsp"><font color="#f00" style="font-weight:bold;">还没有账号？去注册</font></a></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>

</body>
</html>
