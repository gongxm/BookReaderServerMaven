<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户注册</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="css/main.css">

<script type="text/javascript">
	function checkUserName() {

		var username = document.getElementById("username").value;

		if (username.length<3||username.length>16) {
			document.getElementById("user_name_tips").innerHTML = "<font color='red'>用户名必须是3-16个字符!<font>";
			return;
		}
		var regex = /^\w{3,16}$/

		if (regex.test(username)) {

			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {

				//5.判断当前请求的状态
				if (xhr.readyState == 4) {
					//6.获取请求结果：
					eval("var data=" + xhr.responseText);

					//成功
					if (data.errcode == 1) {
						document.getElementById("user_name_tips").innerHTML = "<font color='green'>"
								+ data.errmsg + "<font>";
					} else {
						document.getElementById("user_name_tips").innerHTML = "<font color='red'>"
								+ data.errmsg + "<font>";
					}
				}

			};
			xhr.open('get',
					"${pageContext.request.contextPath}/validateUserName?username="
							+ username)
			xhr.send(null);
			return true;
		} else {
			document.getElementById("user_name_tips").innerHTML = "<font color='red'>用户名只能是数字大小写字母以及下划线!<font>";
		}
		return false;
	}

	function checkPassword() {

		var password = document.getElementById("password").value;
		var regex = /^\w{6,16}$/

		if (password.length<6||password.length>16) {
			document.getElementById("password_tips").innerHTML = "<font color='red'>密码长度必须是6-16位<font>";
		} else {
			if (regex.test(password)) {
				document.getElementById("password_tips").innerHTML = "<font color='green'>恭喜您,密码可以使用<font>";
				return true;
			} else {
				document.getElementById("password_tips").innerHTML = "<font color='red'>密码只能是数字大小写字母以及下划线!<font>";
			}
		}
		return false;
	}
	function checkRePassword() {
		var password = document.getElementById("password").value;
		var repassword = document.getElementById("repassword").value;
		if (repassword == password) {
			document.getElementById("repassword_tips").innerHTML = "<font color='green'>两次输入的密码一致!<font>";
			return true;
		} else {
			document.getElementById("repassword_tips").innerHTML = "<font color='red'>两次输入的密码不一致!<font>";
			return false;
		}
	}

	function check() {
		if (checkUserName() && checkPassword() && checkRePassword()) {
			return true;
		}
		return false;
	}
</script>

</head>
<body>

	<h1 align="center">用户注册</h1>

	<table class="table_center">
		<tr>
			<td align="center">
				<form name="regist"
					action="${pageContext.request.contextPath}/registServlet"
					onsubmit="return check()" method="post">
					<table class="table_inner" style="table-layout: fixed;">
						<tr>
							<td style="width: 120px" align="center">用户名:</td>
							<td style="width: 120px" align="center"><input id="username" type="text" name="username"
								value="${sessionScope.user.username}" onBlur="checkUserName()" />
							</td>
							<td style="width: 200px" align="left"><div id="user_name_tips"></div></td>
						</tr>
						<tr>
							<td style="width: 120px" align="center">密码:</td>
							<td style="width: 120px" align="center"><input id="password" type="password" name="password"
								value="${sessionScope.user.password}" onBlur="checkPassword()" />
							</td>
							<td style="width: 200px" align="left"><div id="password_tips"></div></td>
						</tr>
						<tr>
							<td style="width: 120px" align="center">确认密码:</td>
							<td style="width: 120px" align="center"><input id="repassword" type="password" name="repassword"
								onBlur="checkRePassword()" /></td>
							<td><div id="repassword_tips"></div></td>
						</tr>
						<tr>
							<td align="center" colspan="2"><input type="reset" value="清空" />       <input type="submit" value="注册" /></td>
						</tr>
						<tr>
							<td align="center" colspan="2"><a
								href="${pageContext.request.contextPath}"><font color="#00f">回到主页</font></a>       <a
								href="${pageContext.request.contextPath}/login.jsp"><font color="#f00" style="font-weight:bold;">已有账号,去登陆</font></a></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>
