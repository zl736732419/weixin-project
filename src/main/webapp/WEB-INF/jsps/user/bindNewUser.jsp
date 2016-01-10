<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap/bootstrap-core/bootstrap.min.css">

<script type="text/javascript" charset="utf-8" src="js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/bootstrap/bootstrap-core/bootstrap.min.js"></script>

<title></title>
</head>
<body>
	<h2>绑定新用户</h2>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<form method="post">
					<table style="text-align: center;">
						<tr>
							<td>用户名：</td>
							<td>
								<input type="text" name="username">
							</td>
						</tr>
						<tr>
							<td>密码：</td>
							<td>
								<input type="text" name="password">
							</td>
						</tr>
						<tr>
							<td>确认密码：</td>
							<td>
								<input type="text" name="confirmPwd">
							</td>
						</tr>
					</table>
					<input type="submit" value="确定"><a href="user/bindExistUser">绑定已经存在的用户</a>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<span class="label label-danger">${error}</span>			
			</div>
		</div>
	</div>


</body>
</html>