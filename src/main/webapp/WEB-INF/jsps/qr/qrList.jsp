<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" type="text/css" href="css/menu/menuGroup.css">

<script type="text/javascript" charset="utf-8"
	src="js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/bootstrap/bootstrap-core/bootstrap.min.js"></script>

<title></title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover table-condensed table-bordered" style="text-align: center;">
					<thead>
						<a href="qr/tempList" class="btn btn-default">查询临时二维码</a>&nbsp;
						<a href="qr/foreverList" class="btn btn-default">查询永久二维码</a>&nbsp;
						<a href="qr/add" class="btn btn-default">添加二维码</a>&nbsp;
					</thead>
					<tr class="active">
						<th></th>
						<th>name</th>
						<th>type</th>
						<th>status</th>
						<th>snum</th>
						<th>msg</th>
						<th>qrData</th>
						<th>createTime</th>
						<th>二维码</th>
					</tr>
					<c:forEach items="${list}" var="qr" varStatus="index">
						<c:choose>
							<c:when test="${index.index % 2 == 0 }">
								<tr class="info">
							</c:when>
							<c:otherwise>
								<tr valign="middle">
							</c:otherwise>
						</c:choose>
						<td>${qr.id }</td>
						<td>${qr.name }</td>
						<td>${qr.type }</td>
						<td>${qr.status }</td>
						<td>${qr.snum }</td>
						<td>${qr.msg }</td>
						<td>${qr.qrData }</td>
						<td>${qr.createTime }</td>
						<td><img width="120" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${qr.ticket}"> </td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>