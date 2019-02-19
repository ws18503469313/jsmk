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
<link href="/lay/css/modules/laydate/default/laydate.css">
<link href="/lay/css/modules/layer/default/layer.css">
<link href="/lay/css/modules/code.css">
<link rel="stylesheet" href="/lay/css/layui.css">
</head>
<body>
	<h1>view</h1>
	<button onclick="login()">登陆</button>
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
<script type="text/javascript" src="/lay/layui.all.js"></script>
<script type="text/javascript" src="/lay/layui.js"></script>
<script type="text/javascript">
	function login() {
		layer.open({
        	  type: 2,
        	  area: ['400px', '600px'],
        	  fixed: false, //不固定
        	  btn: ['登陆', '取消'],
        	  maxmin: true,
        	  content: '/html/login.html',
        	  title: '用户登陆',
        	  btn1:function(index,layero){
        			
        		  var detailForm = layer.getChildFrame('form', index);
        		  var url = $('#url', detailForm).val();//直接拿到子窗口的值
        		  var title = $('#title', detailForm).val();//直接拿到子窗口的值
	  	           layer.close(index);
        	  },
	          btn2: function(){
	       		  layer.closeAll();
	       	  }
        	});
	}

</script>
</html>