<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="로그인" name="title"/>
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
	.main{
		vertical-align : middle;
	}
</style>

<form action="login" method="post">
	<div class="container-350 mt-50 mb-20">
		<div class="row center">
			<h1>SALUV</h1>
				<div class="row mt-30">
					<input class="input w-100" type="text" name="memberId" placeholder="아이디" required autocomplete="off">
				</div>
				<div>
					<input class="input w-100" type="password" name="memberPw" placeholder="비밀번호" required>
				</div>
				
				<div class="row mt-20">
					<button type="submit" class="btn btn-netural w-100">로그인</button>
				</div>
				
				<div class="row mt-20">
					<a href="find_id">아이디 찾기</a> &nbsp;|&nbsp;
					<a href="find_pw">비밀번호 찾기</a> &nbsp;|&nbsp;
					<a href="join">회원가입</a>
				</div>
			
			<c:if test="${param.error != null}">
				<h2 style="color:red">입력한 정보가 맞지 않습니다.</h2>
			</c:if>
		</div>
	</div>
</form>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>