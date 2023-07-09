<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="쿠폰 리스트" name="title"/>
	</jsp:include>
	
	<html>			
<head>

<style>
	th, td, h3 {
    	vertical-align : middle;
    	line-height : 1.2em;
    }
    
    a:hover {
		color : #29995d;/* 마우스 올렸을 글자 색 */
		text-decoration:none;
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
	
	td{
    	vertical-align : middle;
    	line-height : 1.2em;
    	font-size : 14px;
    }
    
	a{
		margin-top: 5px;
		margin-bottom: 10px;
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
    	border: 2px solid gray;
    }
</style>

</head>
<body>

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
							<h3><a href=" ${pageContext.request.contextPath}/mypage/order_list">주문내역</a></h3>
							<h3><a href="${pageContext.request.contextPath}/review/list">상품후기</a></h3>
							<h3><a href="${pageContext.request.contextPath}/mypage/coupon">쿠폰</a></h3>
							<h3><a href="${pageContext.request.contextPath}/member/detail?memberId=${memberDto.memberId}">내정보수정</a></h3>
							<h3>도움이 필요하신가요?<br><a href="${pageContext.request.contextPath}/inquire/list">1:1문의하기</a></h3>
						</div>	
					</div>	
				</div>
			</aside>

			<section>
				<div class="float-right w-80 mt-40 mb-40">
				<table class="table table-slit table-border">
					<thead>
						<tr>
							<th>No</th>
							<th>쿠폰명</th>
							<th>할인금액</th>
							<th>조건</th>
							<th>발급일</th>
							<th>만료일</th>
							<th>사용일</th>
							<th>사용여부</th>
						</tr>
					</thead>
					<tbody class="center">
						<c:forEach var = "couponList" items = "${couponList}">
						<tr>
							<td>${couponList.couponNo}</td>
							<td>${couponList.couponName}</td>
							<td>${couponList.couponDiscount}</td>
							<td>${couponList.couponInfo}</td>
							<td>${couponList.couponStartdate}</td>
							<td>${couponList.couponEnddate}</td>
							<td>${couponList.couponUseDate}</td>
							<td>${couponList.couponValid}</td>
						</tr>
						</c:forEach>
					</tbody>	
					<tfoot>
					</tfoot>
				</table>
				</div>
			</section>
		</div>
		
</div>

</body>
</html>
    
    <jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>