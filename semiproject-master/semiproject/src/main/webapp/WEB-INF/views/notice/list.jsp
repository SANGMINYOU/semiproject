<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="공지 게시판" name="title"/>
</jsp:include>

<style>
	.table > thead {
    	font-size: 16px;
    }
    .table > tbody {
    	font-size: 14px;
    }
	
	/* 확장 스타일 : 옆트임 테이블*/
    .table.table-slit > thead > tr,
    .table.table-slit > tfoot > tr{
    	background-color: #efefef;
    	border: 2px solid gray;
    }
    
    /* 확장스타일 : 테두리가 있는 테이블 */
    .table.table-border > thead > th,
    .table.table-border > thead > td,
    .table.table-border > tbody > tr,
    .table.table-border > tbody > td{
    	border: 2px solid gray;
    }
</style>
<c:set var="admin" value="${mg == '관리자'}"></c:set>
<div class = "container-1100 mt-40 mb-40">
	<div class = "row center">
		<h1>NOTICE</h1>
	</div>
	
	<div class="row mt-30">
		<table class="table table-slit table-border"> <!-- border="1" width="1000"> -->
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
					<c:if test="${admin}">
					<th>메뉴</th>
					</c:if>
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach var = "list" items = "${list}">
				<tr>
					<td>${list.noticeNo}</td>
					<td><a href = "detail?noticeNo=${list.noticeNo}">${list.noticeTitle}</a></td>
					<td>${list.noticeId}</td>
					<td>${list.noticeWritedate}</td>
					<td>${list.noticeRead}</td>
					<c:if test="${admin}">
					<td>
						<a class="btn btn-neutral" style="padding:4px;" href = "editAdmin?noticeNo=${list.noticeNo}">수정</a>
						<a class="btn btn-neutral" style="padding:4px;" href = "deleteAdmin?noticeNo=${list.noticeNo}">삭제</a>
					</td>
					</c:if>
				</tr>
				</c:forEach>
			</tbody>
			<c:if test="${admin}">
			<tfoot>
				<tr>
					<td colspan="8" align="right">
						<a href = "write"><i class="fa-solid fa-plus"></i> 등록</a>
					</td>
				</tr>
			</tfoot>
			</c:if>
		</table>
		</div>
	<!-- 검색창 -->
	<div class="row center">
		<form action="list" method="get">
			<input type="hidden" name="size" value="${noticeListSearchVO.size}">
			<select class="input" name="type" required>
				<option value="notice_title" <c:if test="${noticeListSearchVO.type == 'notice_title'}">selected</c:if>>제목</option>
				<option value="notice_id" <c:if test="${noticeListSearchVO.type == 'notice_title'}">selected</c:if>>작성자</option>
			</select>
			
			<input class="input" type="search" name="keyword" placeholder="검색어" required value="${noticeListSearchVO.keyword}">
			
			<button class="btn btn-positive" type="submit">검색</button>
		</form>
	</div>
	
<!-- 페이지 네비게이터  --> 

		<div class="row center">
		<ul class="pagination">
			<!-- 이전 -->
			<c:choose>
				<c:when test="${not noticeListSearchVO.isFirst()}"> <!-- 맨 처음 페이지가 아니라면  -->
					<li><a href="list?p=${noticeListSearchVO.firstBlock()}&${noticeListSearchVO.parameter()}">&laquo;</a></li> <!-- 첫번째 페이지로 이동  -->
				</c:when>
				<c:otherwise> <!-- 그렇지 않으면  -->
					<li><a href="">&laquo;</a></li> <!-- 페이지 변화 없음  -->
				</c:otherwise>
			</c:choose>
			
			<!-- 이전 구간의 마지막 페이지로 이동 -->			
			<c:choose>
				<c:when test="${noticeListSearchVO.hasPrev()}"> <!-- 이전 페이지가 없다면-->	
					<li><a href="list?p=${noticeListSearchVO.prevBlock()}&${noticeListSearchVO.parameter()}">&lt;</a></li> <!-- 이전 구간의 마지막 페이지로 이동-->	
				</c:when>
				<c:otherwise> <!-- 그렇지 않으면  -->
					<li><a href="">&lt;</a></li> <!-- 페이지 변화 없음  -->
				</c:otherwise>
			</c:choose>
			
			<!-- 현재 구간의 페이지 이동 -->
			<!-- 변수명을 i로 하며 시작과 끝은 noticeListSearchVO의 startBlock(), endBlock()의 반환값으로, 간격은 1로 한다  -->
			<c:forEach var="i" begin="${noticeListSearchVO.startBlock()}" end="${noticeListSearchVO.endBlock()}" step="1">
					<a href = "list?p=${i}&${noticeListSearchVO.parameter()}">${i}</a>
			</c:forEach>
			
			<!-- 다음을 누르면 다음 구간의 첫 페이지로 안내 -->
			<c:choose>
				<c:when test="${noticeListSearchVO.hasNext()}"> <!-- 다음페이지가 있으면  -->
					<li><a href="list?p=${noticeListSearchVO.nextBlock()}&${noticeListSearchVO.parameter()}">&gt;</a></li> <!-- 다음 구간의 첫 페이지로 이동  -->
				</c:when>
				<c:otherwise> <!-- 그렇지 않으면  -->
					<li><a href="">&gt;</a></li> <!-- 페이지 변화 없음  -->
				</c:otherwise>
			</c:choose>
			
			<!-- 맨 마지막 페이지로 이동 -->
			<c:choose>
				<c:when test="${not noticeListSearchVO.isLast()}"> <!-- 맨 마지막 페이지가 아니라면 -->
					<li><a href="list?p=${noticeListSearchVO.lastBlock()}&${noticeListSearchVO.parameter()}">&raquo;</a></li> <!-- 맨 마지막 페이지로 이동 -->
				</c:when>
				<c:otherwise>  <!-- 그렇지 않으면  -->
					<li><a href="">&raquo;</a></li> <!-- 페이지 변화 없음  -->
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	</div>
	
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>


