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
<title>境外WIFI后台登录系统</title>
<script src="<%=basePath%>js/jquery-3.2.1.min.js"></script>
<style type="text/css">
body {
	/*设置所有外边距*/
	margin: 0;
	/*设置背景颜色*/
	background-color: #999;
	/*设置字体及字体大小*/
	font: "宋体" 12px;
}

#bg {
	/*设置外边的上边距*/
/* 	margin-top: 25px; */
	/*添加背景图片*/
	background-image: url("<%=basePath%>images/bg.jpg");
/* 	background-repeat: no-repeat; */
background-repeat:inherit;
	/*设置边框的高度*/
	height: 100%;
	/*设置边框的宽度*/
	width: 100%;
	/*设置边框底部的样式*/
	border-bottom-style: solid;
	/*设置边框底部的颜色*/
	border-bottom-color: #FFFFFF;
	/*设置边框底部的线宽*/
	border-bottom-width: 5px;
	/*设置边框顶部的样式*/
	border-top-style: solid;
	/*设置边框顶部的颜色*/
	border-top-color: #FFFFFF;
	/*设置边框顶部的线宽*/
	border-top-width: 5px;
}

#login {
	/*设置登录框高度*/
 	height: 240px; 
	/*设置登录框宽度*/
 	width: 350px; 
	/*设置外边距*/
/* 	margin: 100px 0px 0px 30px; */
	/*设置边框圆角*/
	border-radius: 15px;
	/*设置边框线样式*/
	border: #FFF solid 3px;
	/*设置背景半透明度填充*/
	background: rgba(255, 255, 255, 0.4);
}

h3 {
	/*设置文本居中显示*/
	text-align: center;
	/*设置文本颜色*/
	color: #8f5a0a;
}

#inputs input {
	/*设置文本框填充颜色*/
	background: rgba(255, 255, 255, 0.4)
		url("<%=basePath%>images/login-sprite.png") no-repeat;
	/*设置内容与边框距离*/
	padding: 10px 10px 10px 30px;
	/*设置外边框的距离*/
	margin: 15px 0px 15px 15px;
	/*设置文本框宽度*/
	width: 253px; /* 283 + 2 + 45 = 370 */
	/*设置边框线样式*/
	border: 2px solid #CCC;
	/*设置圆角*/
	border-radius: 5px;
	/*设置字体*/
	font-family: "华文楷体";
	/*设置字体大小*/
	font-size: 12px;
}

#inputs input:focus {
	/*设置背景颜色*/
	background-color: #fff;
	/*设置边框颜色*/
	border-color: #F90;
	/*设置轮廓的样式*/
	outline: none;
	/*设置实现图层阴影效果*/
	-o-box-shadow: 0 0 0 2px #e8c291 inset;
}

#username {
	/*设置背景图像的起始位置*/
	background-position: 5px -8px !important;
}

#password {
	/*设置背景图像的起始位置*/
	background-position: 5px -60px !important;
}

#submitbutton {
	margin: 0px 0px 0px 20px;
	background-color: #ffb94b;
	/*设置背景图像的线性渐变*/
	background-image: -o-linear-gradient(top, #fddb6f, #ffb94b);
	border-radius: 3px;
	/*设置文本阴影*/
	text-shadow: 0 3px 0 rgba(255, 255, 255, 0.5);
	border-width: 1px;
	border-style: solid;
	border-color: #d69e31 #e3a037 #d5982d #e3a037;
	float: left;
	height: 35px;
	padding: 0;
	width: 120px;
	/*当鼠标移动到该单元格上时，鼠标会由箭头形状改为手的形状*/
	cursor: pointer;
	font: bold 15px Arial, Helvetica;
	color: #8f5a0a;
}
</style>
</head>
<script type="text/javascript">
	function keyLogin(){
		if(event.keyCode == 13){
			$("#submitbutton").click();
		}
	}

	function loginsystem() {
		var username = $("#username").val();
		var password = $("#password").val();

		$.ajax({
			type:"POST",
			data:"username="+username+"&password="+password,
			async: false, 
			url:"<%=basePath%>login.action",
			success: function(data) {
			    if(data.loginsuccess){
			    	window.location.href = "<%=basePath%>pages/main.jsp";
				} else {
					alert("用户名或密码错误！");
				}

			},
			error : function(data) {
				alert("用户名或密码错误！");
			}
		});
	}
</script>
<body onkeydown="keyLogin()" style="width: 100%;height:800px;">
	<div id="bg" style="width: 100%;height:800px;">
		<form id="loginform" action="<%=basePath%>login.action" name="forms"
			method="post" style="width: 100%;height:800px;">
			<div id="login">
			<h3>用户登录</h3>
			<div id="inputs">
				<input type="text" id="username" name="username" placeholder="用户名"
					autofocus="autofocus" required="required" /> <input
					type="password" id="password" name="password" placeholder="密码"
					required="required" />
			</div>
			<div id="actions">
				<input type="button" id="submitbutton" value="登录"
					onclick="loginsystem()" />
			</div>
			</div>
		</form>
	</div>
</body>
</html>