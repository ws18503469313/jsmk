<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>??</title>
</head>
<body>
	<div>
		<shiro:authenticated>
			<a>必须登陆才能看到</a>
		</shiro:authenticated>
	</div>
	<div>
		<shiro:hasAnyRoles name="manager, admin">
			<a>有任何一种角色即可</a>
		</shiro:hasAnyRoles>
	</div>
	<div>
		<shiro:hasRole name="manager">
			<a>必须有manager角色</a>
		</shiro:hasRole>
	</div>
	<div>
		<shiro:hasRole name="admin">
			<a>必须有admin角色</a>
		</shiro:hasRole>
	</div>
</body>
</html>