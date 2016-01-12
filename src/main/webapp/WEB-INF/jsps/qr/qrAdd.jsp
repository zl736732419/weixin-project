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
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<form method="post">
					<table style="text-align: center;">
						<tr>
							<td>name：</td>
							<td>
								<input type="text" name="name">
							</td>
						</tr>
						<tr>
							<td>type</td>
							<td>
								<input type="text" name="type">
							</td>
						</tr>
						<tr>
							<td>snum</td>
							<td>
								<input type="text" name="snum">
							</td>
						</tr>
						<tr>
							<td>msg</td>
							<td>
								<input type="text" name="msg">
							</td>
						</tr>
						<tr>
							<td>qrData</td>
							<td>
								<input type="text" name="qrData">
							</td>
						</tr>
					</table>
					<input type="submit" value="确定">
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