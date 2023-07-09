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
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_admin.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@8/swiper-bundle.min.css">

<!-- 글꼴 바꿈 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

<style>
	a:hover {
	text-decoration: none;
	color : #29995d;/* 마우스 올렸을 글자 색 */
	}
	
	/* 확장 스타일 : 줄무늬 테이블*/
    .table.table-slit > thead > tr,
    .table.table-slit > tfoot > tr{
    	background-color: #dfe6e9;
    }
    
    /* 확장스타일 : 테두리가 있는 테이블 */
    .table.table-border {
    	border:1px solid gray;
    }
    .table.table-border > tbody > tr,
    .table.table-border > tbody > tr{
    	border: 1px solid gray;
    }
    
    ul.pagination{
    	font-size: 1.25em;
    }
    
    .table {
    	font-size: 14px;
    }
    
    th, td {
    	vertical-align : middle;
    }
    	}
	.font-menu > h3:hover{
		background-color: #ebf7f2;
	}
	.font-menu > h3{
		margin-top: 30px;
	}
	.w-80 {
		width:80%;
	}
	
	.table > thead {
    	font-size: 16px;
    }
    .table > tbody {
    	font-size: 14px;
    }
	
	/* 확장 스타일 : 옆트임 테이블*/
    .table.table-slit > thead > tr,
    .table.table-slit > thead > th{
    	background-color: #efefef;
    	border: 2px solid gray;
    }
    
    /* 확장스타일 : 테두리가 있는 테이블 */
    .table.table-border > thead > th,
    .table.table-border > thead > td,
    .table.table-border > tbody > tr,
    .table.table-border > tbody > th,
    .table.table-border > tbody > td{
    	border: 1px solid gray;
    }
	
	th, td, h3 {
    	vertical-align : middle;
    	line-height : 1.2em;
    }
	/* div {
		border:1px dotted gray;
	} */
</style>


<div class = "container-1100 mt-40 mb-40">
<div class = "row center mt-40 mb-40">
	<h1>리뷰 목록</h1>
</div>
					
<hr> 
	
	<div class = "row mt-40 mb-40">
	<table border = "1" width = "1000" class="table table-slit table-hover table-border">
		<tbody>
			<tr>
				<th>리뷰 번호</th>
				<th>리뷰 작성자</th>
				<th width="45%">리뷰 제목</th>
				<th>리뷰 작성일</th>
				<th>리뷰 수정일</th>
			</tr>
			
			<c:forEach var = "reviewList" items = "${reviewList}">
			<tr align="center">
				<th>${reviewList.reviewNo}</th>
				<th>${reviewList.reviewId}</th>
				<th><a href = "detail?reviewNo=${reviewList.reviewNo}">${reviewList.reviewTitle}</a></th>
				<th>${reviewList.reviewWritetime}</th>
				<th>${reviewList.reviewUpdatetime}</th>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
<%-- 페이지 이동 --%>
<div class = "row center mt-30">
	<%-- 첫 페이지인지 판정 --%>
	<c:choose>
		<c:when test = "${reviewListSearchVO.isFirst()}"> <%-- 첫 페이지라면 --%>
			<a href = "">&laquo;</a>  <%-- 현재 주소 유지 --%>
		</c:when>
		<c:otherwise> <%-- 그렇지 않다면(첫 페이지가 아니라면) --%>
			<a href = "adminList?pageNow=${reviewListSearchVO.blockFirst()}">&laquo;</a> <%-- 첫 페이지로 이동 --%>
		</c:otherwise>
	</c:choose>
	
	<%-- 이전 페이지의 존재 여부 판정 --%>
	<c:choose>
		<c:when test = "${reviewListSearchVO.hasPrev()}"> <%-- 이전 페이지가 존재한다면 --%>
			<a href = "adminList?pageNow=${reviewListSearchVO.blockPrev()}&${reviewListSearchVO.queryString()}">&lt;</a> <%-- 이전 페이지로 이동 --%>
		</c:when>
		<c:otherwise> <%-- 그렇지 않다면(이전 페이지가 존재하지 않는다면) --%>
			<a href = "#">&lt;</a> <%-- 현재 주소 유지 --%>
		</c:otherwise>
	</c:choose>
	
	<%-- 페이지 블럭 표시 --%>
	<c:forEach var = "i" begin = "${reviewListSearchVO.blockStart()}" end = "${reviewListSearchVO.blockEnd()}" step = "1">
		<a href = "adminList?pageNow=${i}&${reviewListSearchVO.queryString()}">${i}</a>
	</c:forEach>
	
	<%-- 다음 페이지의 존재 여부 판정 --%>
	<c:choose>
		<c:when test = "${reviewListSearchVO.hasNext()}"> <%-- 다음 페이지가 존재한다면 --%>
			<a href = "adminList?pageNow=${reviewListSearchVO.blockNext()}&${reviewListSearchVO.queryString()}">&gt;</a> <%-- 다음 페이지로 이동 --%>
		</c:when>
		<c:otherwise> <%-- 그렇지 않다면(다음 페이지가 존재하지 않는다면) --%>
			<a href = "">&gt;</a> <%-- 현재 주소 유지 --%>
		</c:otherwise>
	</c:choose>
	
	<%-- 마지막 페이지인지 판정 --%>
	<c:choose>
		<c:when test = "${reviewListSearchVO.isLast()}"> <%-- 마지막 페이지라면 --%>
			<a href = "#">&raquo;</a> <%-- 현재 주소 유지 --%>
		</c:when>
		<c:otherwise>
			<a href = "adminList?pageNow=${reviewListSearchVO.blockLast()}&${reviewListSearchVO.queryString()}">&raquo;</a>
		</c:otherwise>
	</c:choose>
	
	<div class="row mt-30">
	<form action = "adminList" method = "get">
		<%-- 일단 상품명 조회만 추가했으며 차후 테이블 조인을 통해 카테고리 이름으로도 조회가 가능하도록 바꿀 예정 --%>
		<select name = "type">
			<option value = "review_title" <c:if test = "${reviewListSearchVO.type == 'review_title'}">selected</c:if>>리뷰제목</option>
		</select>
		<input name = "keyword" value = "${reviewListSearchVO.keyword}">
		<button type = "submit">검색</button>
	</form>
	</div>
</div>

<%-- 검색창 --%>

	
</div>

