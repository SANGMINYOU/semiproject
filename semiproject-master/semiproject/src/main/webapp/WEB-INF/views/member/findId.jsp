<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="아이디 찾기" name="title"/>
</jsp:include>

<style>
	.footer{
		position:fixed;
		bottom:0;
		left:0;
		width:100%;
	}
</style>

<form action="find_id" method="post">
	<div class="container-300 mt-50">
		<div class="row center">
			<h1>아이디 찾기</h1>
		</div>
		
		<div class="row mt-40">
			<input class="input w-100" type="text" name="memberName" placeholder="이름" required><br><br>
		</div>
			
		<div class="row center">
			<input class="input w-100" type="email" name="memberEmail" placeholder="이메일" required><br><br>
		</div>
		
		<div class="row right mb-30">
			<a href="login" class="btn btn-neutral">취소</a>
			<button class="btn" type="submit">확인</button>
		</div>
		
		<c:if test="${param.error != null}">
			<h2 style="color:red">일치하는 정보가 없습니다.</h2>
		</c:if>
	</div>
</form>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>