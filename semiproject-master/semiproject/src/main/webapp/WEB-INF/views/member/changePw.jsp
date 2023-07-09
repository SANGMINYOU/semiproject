<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="비밀번호 변경" name="title"/>
</jsp:include>

<style>
	.footer{
		position:fixed;
		bottom:0;
		left:0;
		width:100%;
	}
</style>

<form action="change_pw" method="post">

<div class="container-300 mt-50">
	<div class="row center">
		<h1>비밀번호 변경</h1>
	</div>

	<div class="row mt-30">
		<input class="input w-100" type="password" name="beforePw" placeholder="현재 비밀번호" required><br><br>
	</div>
	
	<div class="row">
		<input class="input w-100" type="password" name="afterPw" placeholder="새 비밀번호" required>
	</div>
	
	<div class="row right mt-10">
		<a href="${pageContext.request.contextPath}/member/detail?memberId=${memberDto.memberId}" class="btn btn-neutral">취소</a>
		<button class="btn btn-negative" type="submit">변경하기</button>
	</div>
</div>

</form>

<c:if test="${param.error != null}">
	<h2 style="color:red;">잘못된 비밀번호입니다.</h2>
</c:if>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>