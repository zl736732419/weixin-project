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

<script type="text/javascript">
	$(function() {
		var interval = null;
		var num = 0;
		
		interval = setInterval(function() {
			var url = 'user/qrlogin';
			num++;
			if(num >= 20) {
				clearInterval(interval);
				alert("二维码已经失效，请重新刷新页面获取新的二维码!");
			}else {
				var snum = $('#snum').val();
				url += '/' + snum;
				$.post(url, null, function(data) {
					console.info(data);
					if(data == 1) {
						clearInterval(interval);
						window.location.href='user/list';
					}
				});
			}
		}, 3000);
		
		
	});

</script>

<title></title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-offset-2 col-md-8">
				<input type="hidden" id="snum" value="${qr.snum}"/>
				<img src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${qr.ticket}" width="100%" height="100%">
			</div>
		</div>
	</div>
</body>
</html>