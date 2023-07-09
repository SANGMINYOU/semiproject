<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="비밀번호 확인" name="title"/>
</jsp:include>

<style>
	.footer{
		position:fixed;
		bottom:0;
		left:0;
		width:100%;
	}
</style>

<form action="end" method="post">

<div class="container-300 mt-50">
	<div class="row center">
		<h1>비밀번호 확인</h1>
	</div>

	<div class="row mt-40">
		<input class="input w-100" type="password" name="memberPw" required>
	</div>
	
	<div class="row right mb-30">
		<a href="${pageContext.request.contextPath}/member/detail?memberId=${memberDto.memberId}" class="btn btn-neutral">취소</a>
		<button class="btn btn-negative" type="submit">탈퇴</button>
	</div>
</div>
	
</form>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>