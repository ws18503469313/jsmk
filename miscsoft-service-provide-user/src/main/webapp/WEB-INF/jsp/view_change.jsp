<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>" />
<title>jsp</title>
	<link href="/lay/css/modules/laydate/default/laydate.css">
    <link href="/lay/css/modules/layer/default/layer.css">
    <link href="/lay/css/modules/code.css">
    <link rel="stylesheet" href="/lay/css/layui.css">
    
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/lay/layui.all.js"></script>
    <script type="text/javascript" src="/lay/layui.js"></script>
    <style type="text/css">
    	.layui-btn{
		    top: 50%;
		    transform: translateY(-5%);
		}
    </style>

</head>
<body>
	<button id="aaa">aaa</button>
	<div>
		<button class="layui-btn" onclick="login()">用戶登陸</button>
	</div>
</body>
<!-- <script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function () {
		$("#aaa").click(function(){
			console.log("adadadad-----");
		});
		console.log(123);
	})
</script> -->
<%@include file="/common/common.jsp" %>
<script type="text/javascript">
layui.use('layer', function () {
	var layer = layui.layer;
	console.log(123);
})
function login() {
	layer.alert("abc", {icon: 2});
}
</script>
</html>