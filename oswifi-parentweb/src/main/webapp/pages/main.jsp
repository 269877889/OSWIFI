<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=basePath%>js/jquery-3.2.1.min.js"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	/*设置背景颜色*/
	background-color: #999;
	/*设置字体及字体大小*/
	font: "宋体" 12px;
}
</style>
</head>
<script type="text/javascript">

function userlogout(username){
	$.ajax({
		type:"POST",
		data:"username="+username,
		async: false, 
		url:"<%=basePath%>logout.action",
		success: function(data) {
		    window.location.href = "<%=basePath%>login.jsp";
		}
		});
	}
</script>
<body style="width:1200px;height:800px;background-image: url('<%=basePath%>images/main.jpg');" >
	<div style="width: 100%; height: 100%;" >
		<div style="width: 100%; height: 15%;float: right;">
			<a id="loginname" type="text-decoration:none;">${username}</a> <a
				id="logout" type="text-decoration:none;"
				onclick="userlogout('${username}')">注销</a>
		</div>

		<div style="width: 100%; height: 75%"></div>
		<div style="width: 100%; height: 10%"></div>
	</div>
</body>
</html>