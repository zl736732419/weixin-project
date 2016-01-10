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
						<a href="user/add" class="btn btn-default">添加</a>&nbsp;
					</thead>
					<tr class="active">
						<th></th>
						<th>username</th>
						<th>nickname</th>
						<th>openId</th>
						<th>status</th>
						<th>sex</th>
						<th>bind</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${users}" var="user" varStatus="index">
						<c:choose>
							<c:when test="${index.index % 2 == 0 }">
								<tr class="info">
							</c:when>
							<c:otherwise>
								<tr valign="middle">
							</c:otherwise>
						</c:choose>
						<td>${user.id }</td>
						<td>${user.username }</td>
						<td>${user.nickname }</td>
						<td>${user.openId }</td>
						<td>
							<c:choose>
								<c:when test="${user.status == 0}">
									<span class="label label-warning" style="display:inline-block;">未关注</span>
								</c:when>
								<c:otherwise>
									<span class="label label-success" style="display:inline-block;">已关注</span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${user.sex }</td>
						<td>
							<c:choose>
								<c:when test="${user.bind == 0}">
									<span class="label label-warning" style="display:inline-block;">未绑定</span>
								</c:when>
								<c:otherwise>
									<span class="label label-success" style="display:inline-block;">已绑定</span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<div class="btn-group btn-group-xs" role="group" aria-label="">
								<a href="user/delete/${user.id }"
									class="btn btn-default btn-danger">删除</a> <a
									href="user/update/${user.id }" class="btn btn-default">修改</a>
							</div>
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>