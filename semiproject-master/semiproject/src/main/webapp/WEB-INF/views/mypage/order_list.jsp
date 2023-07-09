
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="주문 내역" name="title"/>
</jsp:include>	

<link rel="stylesheet" type="text/css" href="commons.css">

<script type = "text/javascript">
	
</script>

<style>
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
    	border: 2px solid gray;
    }
	
	th, td, h3 {
    	vertical-align : middle;
    	line-height : 1.2em;
    }
	/* div {
		border:1px dotted gray;
	} */
	
	#reviewred{
		background-color: #EF5B0C;
		color: white;
		height: 30px
	}
	#reviewblue{
		background-color: #569956;
		color: white;
		height: 30px
	}
	button{
		border:none;
	}
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
					<div class="row center">
						<div class="row">
							<h2>주문 내역</h2>
						</div>
						<c:forEach var="paymentListVO" items="${paymentListVO}">
							<table class="table table-slit table-border">
								<thead>
									<tr>
										<th colspan="2">${paymentListVO.orderDate} &nbsp; 상품정보</th>
										<th>구매갯수</th> 
										<th>결제금액</th>
									</tr>
								</thead> 
								<tbody>
								    <tr>
								    	<td class="img" width="100" colspan="">
									    	<img width=100 height=100 src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${paymentListVO.productAttachmentNo}">
									    	<c:choose>
										    	<c:when test = "${paymentListVO.paymentReview}">
										    		<button type = "submit" class="row w-80" id = "reviewred" onclick = "location.href='${pageContext.request.contextPath}/review/edit?paymentNo=${paymentListVO.paymentNo}&paymentProductNo=${paymentListVO.productOriginNo}&orderNo=${paymentListVO.paymentOrderNo}';">리뷰수정</button>
										    	</c:when>
										    	<c:otherwise>
										    		<button type = "submit" class="row w-80" id="reviewblue" onclick = "location.href='${pageContext.request.contextPath}/review/write?paymentNo=${paymentListVO.paymentNo}&paymentProductNo=${paymentListVO.productOriginNo}&orderNo=${paymentListVO.paymentOrderNo}';">리뷰작성</button>
										    	</c:otherwise>
									    	</c:choose>
									    </td>
									    <td class="row left" width="400" height="30">
									    	주문번호 : ${paymentListVO.paymentOrderNo} <br>
									    	결제번호 : ${paymentListVO.paymentNo} <br>
									    	상품명 : ${paymentListVO.productName} <br>
									    	상품옵션 : ${paymentListVO.paymentOption} <br>
									    </td>
									    <td width="80">${paymentListVO.paymentCount}</td>
									    <td width="90">${paymentListVO.paymentPrice}</td>
								   </tr>
								</tbody>
					    	</table>
					</c:forEach>
				</div>
			</div>
		</section>
	</div>
</div>

    
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>