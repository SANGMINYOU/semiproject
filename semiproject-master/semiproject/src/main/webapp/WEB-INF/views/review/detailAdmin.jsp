<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="리뷰 관리" name="title"/>
</jsp:include>

<%-- static의 CSS 적용 --%>
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/reset.css">
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/commons.css">
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_commons.css">
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_header.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@8/swiper-bundle.min.css">

<!-- 글꼴 바꿈 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

<style>
#td-top{
	vertical-align : top;
	margin-top: 20px;
}

#th-color{
	background-color: #efefef;
}

#border tr, th{
	border: 1px solid black;
}
</style>



<div class = "container-900 mt-40 mb-40" style="height: 1000px">
	<div class = "row center mt-40 mb-40">
		<h1>리뷰 상세 페이지</h1>
	</div>
	
	<table class="table" id="border" >
			<tbody>
				<tr>
					<th id="th-color">리뷰 번호</th>
					<td>${reviewDto.reviewNo}</td>
					<th id="th-color">리뷰 작성자</th>
					<td align="center" width="35%">${reviewDto.reviewId}</td>
				</tr>
				<tr>
					<th height="60" id="th-color">리뷰 제목</th>
					<td colspan="3" >${reviewDto.reviewTitle}</td>
				</tr>
				<tr>
					<th height="500" id="th-color">리뷰 내용</th>
					<td colspan="3" align="left" id="td-top">${reviewDto.reviewTitle}</td>
				</tr>
				<tr>
					<th id="th-color">리뷰 작성일</th>
					<td>${reviewDto.reviewWritetime}</td>
					<th id="th-color">리뷰 수정일</th>
					<td>${reviewDto.reviewUpdatetime}</td>
				</tr>
				<tr>
					<th id="th-color">리뷰 첨부파일</th>
					<td>
						<c:forEach var = "reviewAttachmentList" items = "${reviewAttachmentList}">
							<img width = "50px" height = "50px" src = "${pageContext.request.contextPath}/attachment/download/inquireImg?attachmentNo=${reviewAttachmentList.attachmentNo}">
						</c:forEach>
					</td>
				</tr>
				
			</tbody>
		</table>
</div>	
	
	
	
	
