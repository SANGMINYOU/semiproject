<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="문의 관리" name="title"/>
</jsp:include>

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


<div class = "container-1200">

	<div class = "row">
		<h1>1:1 문의글</h1>
	</div>
	
	<div class = "row">
	<table border = "1" width = "1000" class="table table-slit table-hover table-border">
		<tbody>
			<tr>
				<th>문의글 번호</th>
				<th>문의글 작성자</th>
				<th width="45%">문의글 제목</th>
				<th>문의글 작성일</th>
				<th>문의글 수정일</th>
				<th>문의글 답변 상태</th>
			</tr>
			
			<c:forEach var = "inquireList" items = "${inquireList}">
			<tr>
				<th>${inquireList.inquireNo}</th>
				<th>${inquireList.inquireId}</th>
				<th><a href = "${pageContext.request.contextPath}/detail?inquireNo=${inquireList.inquireNo}">${inquireList.inquireTitle}</a></th>
				<th>${inquireList.inquireWritetime}</th>
				<th>${inquireList.inquireUpdatetime}</th>
				<th>${inquireList.inquireHasReply}</th>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<%-- 검색창 --%>
<div align="center" class= "row">
<form action = "listAdmin" method = "get">
	<%-- 일단 상품명 조회만 추가했으며 차후 테이블 조인을 통해 카테고리 이름으로도 조회가 가능하도록 바꿀 예정 --%>
	<select name = "type" style="height:56px;" >
		<option value = "inquire_title"   <c:if test = "${inquireListSearchVO.type == 'inquire_title'}">selected</c:if>>제목</option>
		<option value = "inquire_id"   <c:if test = "${inquireListSearchVO.type == 'inquire_id'}"></c:if>>아이디</option>
	</select>
	<input class="input" name = "keyword" value = "${inquireListSearchVO.keyword}">
	<button style="width:40px; height:56px;" type = "submit">검색</button>
</form>
</div>
	
<%-- 페이지 이동 --%>
<div class = "row center">
	<%-- 첫 페이지인지 판정 --%>
	<c:choose>
		<c:when test = "${inquireListSearchVO.isFirst()}"> <%-- 첫 페이지라면 --%>
			<a href = "">&laquo;</a>  <%-- 현재 주소 유지 --%>
		</c:when>
		<c:otherwise> <%-- 그렇지 않다면(첫 페이지가 아니라면) --%>
			<a href = "${pageContext.request.contextPath}/listAdmin?pageNow=${inquireListSearchVO.blockFirst()}">&laquo;</a> <%-- 첫 페이지로 이동 --%>
		</c:otherwise>
	</c:choose>
	
	<%-- 이전 페이지의 존재 여부 판정 --%>
	<c:choose>
		<c:when test = "${inquireListSearchVO.hasPrev()}"> <%-- 이전 페이지가 존재한다면 --%>
			<a href = "${pageContext.request.contextPath}/listAdmin?pageNow=${inquireListSearchVO.blockPrev()}&${inquireListSearchVO.queryString()}">&lt;</a> <%-- 이전 페이지로 이동 --%>
		</c:when>
		<c:otherwise> <%-- 그렇지 않다면(이전 페이지가 존재하지 않는다면) --%>
			<a href = "#">&lt;</a> <%-- 현재 주소 유지 --%>
		</c:otherwise>
	</c:choose>
	
	<%-- 페이지 블럭 표시 --%>
	<c:forEach var = "i" begin = "${inquireListSearchVO.blockStart()}" end = "${inquireListSearchVO.blockEnd()}" step = "1">
		<a href = "${pageContext.request.contextPath}/listAdmin?pageNow=${i}&${inquireListSearchVO.queryString()}">${i}</a>
	</c:forEach>
	
	<%-- 다음 페이지의 존재 여부 판정 --%>
	<c:choose>
		<c:when test = "${inquireListSearchVO.hasNext()}"> <%-- 다음 페이지가 존재한다면 --%>
			<a href = "${pageContext.request.contextPath}/listAdmin?pageNow=${inquireListSearchVO.blockNext()}&${inquireListSearchVO.queryString()}">&gt;</a> <%-- 다음 페이지로 이동 --%>
		</c:when>
		<c:otherwise> <%-- 그렇지 않다면(다음 페이지가 존재하지 않는다면) --%>
			<a href = "">&gt;</a> <%-- 현재 주소 유지 --%>
		</c:otherwise>
	</c:choose>
	
	<%-- 마지막 페이지인지 판정 --%>
	<c:choose>
		<c:when test = "${inquireListSearchVO.isLast()}"> <%-- 마지막 페이지라면 --%>
			<a href = "#">&raquo;</a> <%-- 현재 주소 유지 --%>
		</c:when>
		<c:otherwise>
			<a href = "${pageContext.request.contextPath}/listAdmin?pageNow=${inquireListSearchVO.blockLast()}&${inquireListSearchVO.queryString()}">&raquo;</a>
		</c:otherwise>
	</c:choose>
</div>
	
	
	</div>
</div>