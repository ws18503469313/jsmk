<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<!-- 一个用户只能拿拥有一个角色 -->
<title>userRoleManager</title>
<link href="/lay/css/modules/laydate/default/laydate.css">
<link href="/lay/css/modules/layer/default/layer.css">
<link href="/lay/css/modules/code.css">
<link rel="stylesheet" href="/lay/css/layui.css">
</head>
<body>
	<form class="layui-form" action="#" >
		<div class="layui-form-item">
			<label class="layui-form-label">请选择角色</label>
			<div class="layui-input-block">
				<c:forEach items="${roles }" var="n">
					<div>
					<input type="radio" name="newRoleID" value="${n.id }" title="${n.rolename }"
						<c:if test="${userRole.roleID eq n.id }">checked</c:if> /></div>
				</c:forEach>
			</div>
		</div>
		<input type="text" style="display: none;" name="UID" value="${userRole.UID }">
		<input type="text" style="display: none;" name="roleID" value="${userRole.roleID }">
		 <!-- <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" type="button"  onclick="subForm()" >立即提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		  </div> -->
		      
	</form>
</body>
<script type="text/javascript" src="/lay/layui.all.js"></script>
<script type="text/javascript" src="/lay/layui.js"></script>
<script type="text/javascript">

/* function subForm() {
	layui.use('jquery', function () {
		var roleId = '${userRole.roleID}';
		var userId = '${UID}';
		console.log(userId);
		var $ =  layui.jquery;
		var checkedId = $("input[type='radio']:checked").val();
		if(roleId == checkedId || roleId == ""){
			return;
		}
		
	})
	
} */
</script>
</html>