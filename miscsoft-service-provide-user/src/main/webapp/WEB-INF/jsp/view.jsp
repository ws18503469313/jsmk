<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + ":" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-W3CDTD HTML 4.01 TransitionalEN">
<html>
<head>
<base href="<%=basePath%>">
<title>京师厚沐</title>
<link href="/lay/css/modules/laydate/default/laydate.css">
<link href="/lay/css/modules/layer/default/layer.css">
<link href="/lay/css/modules/code.css">
<link rel="stylesheet" href="/lay/css/layui.css">
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${user ne null }">
				<button class="layui-btn" id="login" >欢迎[${user.name }]登陆</button>
			</c:when>
			<c:otherwise>
				<button class="layui-btn" id="login" onclick="login(this)">用户登陆</button>
				<button class="layui-btn" id="registe" onclick="registe()">用户注册</button>
			</c:otherwise>
		</c:choose>
	</div>
	<div>
		<label>搜索</label>
		<div>
			<input type="text" id="keyword" value="${note.name }" placeholder="请输入帖子关键字" />
			<button class="layui-btn"  onclick="search()">查询</button>
		</div>
	</div>
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
						<label>名字</label><a>${n.name }</a> <label>发布时间</label><a>${n.publishTime }</a>
						<label><a onclick="like(this)" value="${n.id }"><i class="layui-icon layui-icon-star-fill"  style="color: red;font-size: 30px;"></i></a>${n.like }</label>
						<a href="${ctx }/index/detail?id=${n.id }" target="_blank">查看详情</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
<%-- <%@include file="/common/common.jsp" %> --%>
<script type="text/javascript" src="/lay/layui.all.js"></script>
<script type="text/javascript" src="/lay/layui.js"></script>
<script type="text/javascript">
layui.use('layer', function () {
	var layer = layui.layer;
})
var $ = layui.$;
function login(obj) {
	var $ = layui.$;
//	console.log();
	layer.open({
    	  type: 2,
    	  area: ['400px', '300px'],
    	  fixed: false, //不固定
    	  btn: ['登陆', '关闭'],
    	  maxmin: true,
    	  content: '/html/login.html',
    	  title: '用户登陆',
    	  btn1:function(index,layero){
    		  var detailForm = layer.getChildFrame('form', index);
    		  var username = $('#username', detailForm).val();//直接拿到子窗口的值
    		  var password = $('#password', detailForm).val();//直接拿到子窗口的值
//    		  console.log(username+"---------------"+password);
    		  $.ajax({
    			  	url: "/main/ajaxLogin",
		            type: "POST",
		            data: {'username':username,'password':password},
		            dateType:"json",
		            success: function (data) {
		            	console.log(data);
		            	layer.close(index);
		            	layer.alert(data.msg, {icon: 1});
		            	obj.innerHTML = "欢迎["+data.data.name+"]登陆";
		            	$('#registe').hide();
		            },
		            error: function (data) {
		            	layer.alert(data.msg, {icon: 2});
		            	return false;
					}
	            
	        	});
    	  },
    	  btn2: function(){
       		  layer.closeAll();
       	  }
    	});
}
function registe() {
	var $ = layui.$;
	layer.open({
    	  type: 2,
    	  area: ['500px', '500px'],
    	  fixed: false, //不固定
    	  btn: ['提交', '关闭'],
    	  maxmin: true,
    	  content: '/html/registe.html',
    	  title: '用户注册',
    	  btn1:function(index,layero){
    		    var detailForm = layer.getChildFrame('form', index);
    		    var password = $('#password', detailForm).val();
    		  	var confirm = $('#confirm', detailForm).val();
    		  	if(password != confirm){
    		  	  layer.alert("两次输入密码不一致", {icon: 2});
    		  	  return false;
    		  	}
    		  	var username = $('#username', detailForm).val();
    		  	var name = $('#name', detailForm).val();
    		  	if(username == "" || name =="" ){
    			  	  layer.alert("请补全信息", {icon: 2});
    			  	  return false;
    			} 
    		  	$.ajax({
    			  	url: "/addUser",
    		        type: "POST",
    		        data: {'username':username, 'password':password,'name':name},
    		        dateType:"json",
    		        success: function (result) {
    		        	if(result.status == 200){
    		        		layer.close(index);
    		            	layer.alert(result.msg, {icon: 1});
    		        	}else{
    		        		layer.alert(result.msg, {icon: 2});
    		            	return false;
    		        	}
    		        	
    		        },
    		        error: function (result) {
    				}
    		    
    			});
    	  },
    	  btn2: function(){
       		  layer.closeAll();
       	  }
    	});
}
function like(o) {
	var user = '${user}';
	if(user == ""){
		layer.alert("请登陆后再操作",{icon:2});
		return;
	}
	var $ = layui.$;
	var obj = $(o);
	var id = obj.attr("value");
	console.log(id);
	$.ajax({
	  	url: "/note/like",
        type: "POST",
        data: {'id': id},
        dateType:"json",
        success: function (result) {
//        	console.log(result+"-----------------------------------");
        	if(result.status == 200){
            	layer.alert(result.msg, {icon: 1});
        	}else{
        		layer.alert(result.msg, {icon: 2});
            	return false;
        	}
        	
        },
        error: function (result) {
//        	console.log(result+"-----------------------------------");
		}
    
	});
	
}

function search() {
	
	var keyword = $('#keyword').val();
	if(keyword == "" || keyword.lenth == 0){
		return;
	}
	console.log(keyword);
	window.location.href='${ctx}/index/?name='+keyword;
}
</script>
</html>