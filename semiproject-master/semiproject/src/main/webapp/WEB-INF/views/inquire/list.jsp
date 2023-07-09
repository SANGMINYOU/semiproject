<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="1:1 문의글 목록" name="title"/>
</jsp:include>

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

<div class="container-1100 mt-40 mb-40">
	<header>
		<div class="row center">
			<h3>${memberDto.memberName}님 환영합니다</h3>
		</div>
		<div class="row center">
			<h3>
				등급 : ${memberDto.memberGrade}  / 적립금 : ${memberDto.memberPoint}원 / 쿠폰 :${couponUsable}개
			</h3>
			<br><br><br>
		</div>
	</header>
	
	<div class="float-container">
		<aside>
			<div class="float-left">
				<div class="row center">
					<div class="row mb-30">
						<h2>마이샐러브</h2>
					</div>
					<hr>
					<div class="font-menu">
						<h3><a href="${pageContext.request.contextPath}/mypage/order_list">주문내역</a></h3>
						<h3><a href="${pageContext.request.contextPath}/review/list">상품후기</a></h3>
						<h3><a href="${pageContext.request.contextPath}/mypage/coupon">쿠폰</a></h3>
						<h3><a href="${pageContext.request.contextPath}/member/detail?memberId=${memberDto.memberId}">내정보수정</a></h3>
						<h3>도움이 필요하신가요?<br><a href="${pageContext.request.contextPath}/inquire/list">1:1문의하기</a></h3>
					</div>	
				</div>	
			</div>
		</aside>
			
		<section>
			<div class="float-right w-80">
				<div class = "row">
					<h1 class="mb-30">1:1 문의글 목록</h1>
				</div>
				
				<hr> 
	
				<div class = "row">
	
					<table border = "1" width = "1000" class="table table-slit table-hover table-border">
						<tbody>
							<tr>
								<th width="45%">문의글 제목</th>
								<th>문의글 작성일</th>
								<th>문의글 수정일</th>
								<th>답변 상태</th>
								
								<th colspan = "2">문의글 관리</th>
							</tr>
							<c:forEach var = "inquireList" items = "${inquireList}">
								<c:if test = "${!inquireList.inquireInactive}">
									<tr align="center">
										<td><a href = "${pageContext.request.contextPath}/inquire/detail?inquireNo=${inquireList.inquireNo}">${inquireList.inquireTitle}</a></td>
										<td>${inquireList.inquireWritetime}</td>
										<td>${inquireList.inquireUpdatetime}</td>
										<td>
											<c:choose>
												<c:when test = "${inquireList.inquireHasReply}">
													답변 완료
												</c:when>
												<c:otherwise>
													대기중
												</c:otherwise>
											</c:choose>
										</td>
										<td><a href = "${pageContext.request.contextPath}/inquire/edit?inquireNo=${inquireList.inquireNo}">수정</a></td>
										<td><a href = "${pageContext.request.contextPath}/inquire/delete?inquireNo=${inquireList.inquireNo}">삭제</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
	
					<div class = "row right">
						<a href = "${pageContext.request.contextPath}/inquire/write">1:1문의하기</a>
					</div>
				</div>
	
			</div>
		</section>
	</div>
</div>


<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>