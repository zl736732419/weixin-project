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
						<a href="group/add" class="btn btn-default">添加用户组</a>&nbsp;
						<a href="group/findUserGroup" class="btn btn-default">查询用户所在分组</a>&nbsp;
					</thead>
					<tr class="active">
						<th>id</th>
						<th>name</th>
						<th>count</th>
					</tr>
					<c:forEach items="${groups}" var="group" varStatus="index">
						<c:choose>
							<c:when test="${index.index % 2 == 0 }">
								<tr class="info">
							</c:when>
							<c:otherwise>
								<tr valign="middle">
							</c:otherwise>
						</c:choose>
						<td>${group.id }</td>
						<td>${group.name }</td>
						<td>${group.count }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>