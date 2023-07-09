<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="1:1 문의글 수정 페이지" name="title"/>
</jsp:include>


<div class = "container-900">

	<div class = "row mt-40">
		<h1>1:1 문의글 수정하기</h1>		
	</div>
	
	<hr>
		<form action = "edit" method = "post">
			<input type = "hidden" name = "inquireNo" value = "${inquireDto.inquireNo}">
			<div class = "row">	
				<input class="input input-underline w-50" name = "inquireTitle" value = "${inquireDto.inquireTitle}" type="text">
			</div>
			
			<textarea name = "inquireContent" class="input w-100 fix-size mt-10" rows="8" >${inquireDto.inquireContent}</textarea>
			
			<div class = "row right">
				<button type = "submit" class="btn">수정</button>
			</div>
		</form>
</div>


<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>