<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>jsp</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<body>
	<h1>view</h1>
	<div>
		<h2>分类</h2>
		<ul>
			<c:forEach items="${categray }" var="c">
				<li>${c.name }</li>
			</c:forEach>
		</ul>
	</div>
	<div>
		<h2>帖子</h2>
		<ul>
			<c:forEach items="${notes }" var="n">
				<li>
					<div>
						<label>名字</label><a>${n.name }</a>
						<label>发布时间</label><a>${n.publishTime }</a>
						<label>被喜欢次数</label><a>${n.like }</a>
						<a href="${ctx }/index/detail?id=${n.id }">查看详情</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>