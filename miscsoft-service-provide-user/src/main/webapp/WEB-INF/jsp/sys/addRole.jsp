<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<link href="/lay/css/modules/laydate/default/laydate.css">
<link href="/lay/css/modules/layer/default/layer.css">
<link href="/lay/css/modules/code.css">
<link rel="stylesheet" href="/lay/css/layui.css">
</head>
<body>
	<form class="layui-form" >
		<div class="layui-form-item">
			<label class="layui-form-label">角色名称</label>
			<div class="layui-input-inline">
				<input type="text" id="rolename" name="rolename" value="${role.rolename }" required lay-verify="required" placeholder="请输入角色名称" 
				autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">描述</label>
			<div class="layui-input-inline">
				<input type="text" id="desc" name="desc" value="${role.descprit }" required lay-verify="required" placeholder="请输入描述" 
				autocomplete="off" class="layui-input">
			</div>
		</div>
	</form>

</body>
</html>