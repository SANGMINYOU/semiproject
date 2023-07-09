<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value= "내가 작성한 상품 리뷰" name="title"/>
</jsp:include>

<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hiphop5782/score@latest/score.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".star-score").score({
			starColor:"red",//별 생상(기본 : 금색)
			integerOnly:true,//true를 쓰면 소숫점이 반올림
			display:{
				showNumber:true,//숫자표시
				placeLimit:2,//소수점 자리수
				textColor:"red",//숫자 색상(기본 : 금색)
			}
		});
	});
</script>

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
    	font-size : 12px;
    }
    
	a{
		margin-top: 5px;
		margin-bottom: 10px;
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
				<div class = "float-right w-80 mt-40 mb-40">
					<c:forEach var = "reviewList" items = "${reviewList}">
						<div class="row center">
							${reviewList.reviewWritetime} ${reviewList.productName}

						</div>
							<table class="table mt-20 mb-20">
								<tr>
									<td class="img center" width="100">
										<img width=80 height=80 src="${pageContext.request.contextPath}/attachment/download/reviewImg?attachmentNo=${reviewList.reviewAttachmentNo}"><br>
									</td>
									<td class="row left" width="300" height="30">
										리뷰 제목 : ${reviewList.reviewTitle}
										<br>
										상품 옵션 : ${reviewList.paymentOption}
										<br>
										리뷰 내용 : ${reviewList.reviewContent}
										<br>
									</td>
									<td class="row center" width="100">
										상품 갯수 : ${reviewList.paymentCount}
										<div class="row">
											<div class="star-score" data-max="5" data-rate="${reviewList.reviewGood}"></div>
										</div>
									</td>
									<td class="row center" width="100">
										<a class="btn btn-neutral" style="padding:4px;" href="${pageContext.request.contextPath}/review/edit?reviewNo=${reviewList.reviewNo}">수정</a>
										<a class="btn btn-neutral" style="padding:4px;" href="${pageContext.request.contextPath}/review/delete?reviewNo=${reviewList.reviewNo}">삭제</a>
									</td>
								</tr>
							</table>
							<hr>
					</c:forEach>
				</div>
			</section>

		</div>
		
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>