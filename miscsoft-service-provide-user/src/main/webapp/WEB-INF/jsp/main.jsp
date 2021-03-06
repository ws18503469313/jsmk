<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="/favicon.ico" />
	<link rel="bookmark"href="/favicon.ico" />
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>main_MAIN</title>
  
</head>
<body class="layui-layout-body">

<%@ include file="/common/Base.html" %>

<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">${user.name }</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">控制台</a></li>
      <li class="layui-nav-item"><a href="">商品管理</a></li>
      <li class="layui-nav-item"><a href="">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          贤心
        </a>
        <dl class="layui-nav-child">
          <dd><a href="/html/shiro.html">shiro测试</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="${ctx }/main/logout">退了</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
	      <c:forEach items="${tag }" var="n">
	      		<li class="layui-nav-item " >
	      			<a class="" href="${n.value }">${n.name }</a>
	      			<dl class="layui-nav-child">
	      				<c:forEach items="${n.list }" var="c">
	      					<dd><a href="${ctx }/${c.value}" target="option">${c.name }</a></dd>
	      				</c:forEach>
	      			</dl>
	      		</li>
	      	
	      </c:forEach>
	    <li class="layui-nav-item"><a href="/main/shiroTag" target="option">shiro测试jsp</a></li>
        <li class="layui-nav-item"><a href="/html/shiro.html" target="option">shiro测试html</a></li>
     </ul>
      
<%--       
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">所有商品</a>
          <dl class="layui-nav-child">
            <dd><a href="${ctx }/html/main.html" target="option">测试1</a></dd>
            <dd><a href="${ctx }/html/categray/categray.html" target="option">菜单管理</a></dd>
            <dd><a href="${ctx }/html/note/note.html" target="option">发帖</a></dd>
            <dd><a href="">超链接</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">系统管理</a>
          <dl class="layui-nav-child">
          	<dd><a target="option">人员管理</a></dd>
            <dd><a href="${ctx }/html/sys/access.html" target="option">系统权限</a></dd>
            <dd><a href="${ctx }/html/sys/role.html" target="option">角色管理</a></dd>
            
          </dl>
        </li>
 --%>       
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <iframe id="option" name="option" src="${ctx }/html/main.html" style="overflow: visible;" 
    			scrolling="no" frameborder="no" width="100%" height="100%"></iframe>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © layui.com - 底部固定区域
  </div>
</div>

<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
</body>
</html>