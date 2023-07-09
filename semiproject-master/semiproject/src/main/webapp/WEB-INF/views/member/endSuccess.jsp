<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="회원 탈퇴 완료" name="title"/>
</jsp:include>

<style>
	a{
	text-decoration: none;
	}
	.footer{
		position:fixed;
		bottom:0;
		left:0;
		width:100%;
	}
</style>

<div class="container-500 mt-50">
	<div class="row center">
		<h1>탈퇴가 완료되었습니다.</h1>
	</div>
	
	<br><br>
	
	<div class="row center mb-30">
		<h2>
			<a href="${pageContext.request.contextPath}/">
				<i class="fa-solid fa-house"></i> 홈
			</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="https://www.google.com">
				<i class="fa-brands fa-google"></i> Google
			</a>
		</h2>
	</div>
</div>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>