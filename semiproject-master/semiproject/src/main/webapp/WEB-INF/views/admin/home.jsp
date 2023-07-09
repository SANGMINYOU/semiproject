<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="관리자 페이지" name="title"/>
</jsp:include>
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_admin.css">

<script type="text/javascript">
$(document).ready(function(){
	//menu v2
	$(".menu-v2").mouseover(function(){
		$(this).children(".submenu").show(200);
	});
	$(".menu-v2").mouseleave(function(){
		$(this).children(".submenu").hide(200);
	});
	
});
</script>
