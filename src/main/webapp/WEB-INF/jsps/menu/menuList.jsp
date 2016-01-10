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
				<table class="table table-hover table-condensed table-bordered">
					<thead>
						<a href="menu/add" class="btn btn-default">添加</a>&nbsp;
						<a href="menu/publish" class="btn btn-primary">发布</a>
					</thead>
					<tr class="active">
						<th></th>
						<th>name</th>
						<th>type</th>
						<th>url</th>
						<th>responseType</th>
						<th>content</th>
						<th>key</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${menus}" var="menu" varStatus="index">
						<c:choose>
							<c:when test="${index.index % 2 == 0 }">
								<tr class="info">
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
						<td>${menu.id }</td>
						<td>${menu.name }</td>
						<td>${menu.type }</td>
						<td>${menu.url }</td>
						<td>${menu.responseType }</td>
						<td>${menu.content }</td>
						<td>${menu.menuKey }</td>
						<td>
							<div class="btn-group btn-group-xs" role="group" aria-label="">
								<a href="menu/delete/${menu.id }"
									class="btn btn-default btn-danger">删除</a> <a
									href="menu/update/${menu.id }" class="btn btn-default">修改</a>
							</div>
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div id="programGroupTree">
					<c:forEach items="${tree}" var="menu">
						<div class="portal">
							<c:choose>
								<c:when test="${fn:length(menu.sub_button) <= 0}">
									<h5 class="menu-title">
								</c:when>
								<c:otherwise>
									<h5 class="menu-title active">
								</c:otherwise>
							</c:choose>
								<span class="portal_arrow"></span> <span>${menu.name }</span> <input
									type="hidden" value="${menu.id }" name="id">
							</h5>
							<div style="display: block;" class="menu-body">
								<ul>
								<c:forEach items="${menu.sub_button }" var="subMenu">
									<li><a href="javascript:void(0)">${subMenu.name }</a></li>
								</c:forEach>
								</ul>
							</div>
						</div>

					</c:forEach>
				</div>
			</div>
		</div>
	</div>


</body>
</html>