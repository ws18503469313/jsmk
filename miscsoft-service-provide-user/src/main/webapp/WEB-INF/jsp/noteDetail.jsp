<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<body>
	<div>
		<h2>noteDetail</h2>
		<div>
			<h1>${note.name }</h1>
			<label>发布时间</label><a>${note.publishTime }</a>

		</div>
		<div>
			<c:forEach items="${note.details }" var="i">
				<div>
					<c:choose>
						<c:when test="${i.type  eq 0}">
							<p>${i.content }</p>
						</c:when>
						<c:when test="${i.type eq 1 }">
							<img alt="图片" src="${i.content }">
						</c:when>
						<c:when test="${i.type eq 2 }">
							<video id="video" controls preload="auto" > 
								<source src="/video/8b40143e3d3c594dd7c01eeef42082df.mp4">
							</video>
						</c:when>
					</c:choose>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>