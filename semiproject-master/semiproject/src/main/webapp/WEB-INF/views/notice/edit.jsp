<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="공지 게시판 글 수정 페이지" name="title"/>
</jsp:include>

<div class="container-800">
	<div class = "row mb-40">
		<h1>공지 게시판 글 수정 페이지</h1>		
	</div>
	
		<form action = "editAdmin" method = "post">
			<input type = "hidden" name = "noticeNo" value = "${noticeDto.noticeNo}">
			<div class = "row">	
				<input class="input input-underline w-50" name = "noticeTitle" value = "${noticeDto.noticeTitle}" type="text">
			</div>
			
			<textarea name = "noticeContent" class="input w-100 fix-size mt-10" rows="8" >${noticeDto.noticeContent}</textarea>
			
			<div class = "row right">
				<a href="${pageContext.request.contextPath}/mypage/order_list" class="btn btn-neutral">취소</a>
				<button type = "submit" class="btn">작성</button>
			</div>
		</form>
</div>


<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>