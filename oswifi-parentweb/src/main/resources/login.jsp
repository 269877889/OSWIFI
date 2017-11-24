<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
	<form action="login" name="forms">
		<h3>用户登录</h3>
		<div id="inputs">
			<input type="text" id="username" placeholder="用户名" autofocus="autofocus" required="required"/>
			<input type="password" id="password" placeholder="密码" required="required"/>
		</div>
		<div id="actions">
			<input type="submit" id="submit" value="登录">
		</div>
	</form>
</body>
</html>