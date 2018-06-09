<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet">
	
<style>
 .nav_color{
 	color:#f8ac59;
 }
 
</style>
</head>
<header>
  <div id="mnav">
    <h2><span class="navicon"></span></h2>
    <ul>
      <li><a href="${pageContext.request.contextPath}/index"><i class="fa fa-home"></i> 网站首页</a></li>
      <li><a href="${pageContext.request.contextPath}/list"><i class="fa fa-code"></i> 技术博客</a></li>
      <li><a href="${pageContext.request.contextPath}/summary"><i class="fa fa-folder-o"></i> 知识总结</a></li>
      <li><a href="${pageContext.request.contextPath}/resource"><i class="fa fa-share-alt"></i> 资源分享</a></li>
      <li><a href="${pageContext.request.contextPath}/find/13015.html"><i class="fa fa-tv"></i> 网站介绍</a></li>
      <li><a href="${pageContext.request.contextPath}/about"><i class="fa fa-heart-o"></i> 关于我</a></li>
      <li><a href="${pageContext.request.contextPath}/message"><i class="fa fa-commenting-o"></i> 留言</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/"><i class="fa fa-sign-in"></i> 登录</a></li>
    </ul>
  </div>
  <nav id="nav">
    <ul>
      <li class="1"><a class="" href="${pageContext.request.contextPath}/index"><i class="fa fa-home "></i> 网站首页</a></li>
      <li class="2"><a class="" href="${pageContext.request.contextPath}/list"><i class="fa fa-code"></i> 技术博客</a></li>
      <li class="3"><a class="" href="${pageContext.request.contextPath}/summary"><i class="fa fa-folder-o"></i> 知识总结</a></li>
      <li class="4"><a class="" href="${pageContext.request.contextPath}/resource"><i class="fa fa-share-alt"></i> 资源分享</a></li>
      <li class="5"><a class="" href="${pageContext.request.contextPath}/find/13015.html"><i class="fa fa-tv"></i> 网站介绍</a></li>
      <li class="6"><a class="" href="${pageContext.request.contextPath}/about"><i class="fa fa-heart-o"></i> 关于我</a></li>
      <li class="7"><a class="" href="${pageContext.request.contextPath}/message"><i class="fa fa-commenting-o"></i> 留言</a></li>
      <li class="8"><a class="" href="${pageContext.request.contextPath}/admin/"><i class="fa fa-sign-in"></i> 登录</a></li>
    </ul>
  </nav>
 	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script>
  $(window).scroll(function(){
		if($(document).scrollTop()>70){
			$("nav").addClass("animated fadeOutUp");
			$("nav").removeClass("fadeInDown");
		}
		if($(document).scrollTop()==0){
			$("nav").addClass("animated fadeInDown");
			$("nav").removeClass("fadeOutUp");
		 }
	});
	</script>
</header>

