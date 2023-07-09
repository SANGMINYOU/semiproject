<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="관심 상품" name="title"/>
	</jsp:include>
	

	
	<div class="container-1100 mt-40 mb-40">
		<div class="row center">
		<hr>
		<h3>${memberDto.memberName}님 환영합니다</h3>
		<h3>등급 : ${memberDto.memberGrade}  / 적립금 : ${memberDto.memberPoint}원 / 쿠폰 : ${couponCountVo.cnt}개  
		 </h3>
		 <hr>
	
		</div>
	</div>
	
	<div class="row float-container">
	
		<div class="float-left w-40 p-20">
			<div class="row">
				<h2>마이샐러브</h2>
				<hr>
					<h3><a href="${pageContext.request.contextPath}/mypage/order_list">주문내역</a></h3>
					<h3><a href="${pageContext.request.contextPath}/review/list">상품후기</a></h3>
					<h3><a href="${pageContext.request.contextPath}/mypage/coupon">쿠폰</a></h3>
					<h3><a href="${pageContext.request.contextPath}/member/detail?memberId=${memberDto.memberId}">내정보수정</a></h3>
					<h3>도움이 필요하신가요?<br><a href="${pageContext.request.contextPath}/inquire/list">1:1문의하기</a></h3>		
			</div>
		</div>
			
			
		<div class="row float-container">
			<div class="row left">
				<h2>위시리스트</h2><small>찜한 상품은 최대 50개 까지 저장됩니다</small>
				
				</div>
					<div class="row center w-40 p-20">	
					<hr>
					<div class="row order">
						<div class="row left">
						상품명 : ㅇㅇㅇㅇㅇㅇ <br><br>
						가격 : ㅇㅇㅇㅇㅇㅇ
						<br><br>
						<button>삭제</button> <button>담기</button>					
						</div>				
					</div>				
				<hr>
								
				<div class="row order">
					<div class="row left">
					상품명 : ㅇㅇㅇㅇㅇㅇ <br><br>
					가격 : ㅇㅇㅇㅇㅇㅇ
					<br><br>
					<button>삭제</button> <button>담기</button>					
					</div>				
				</div>				
				<hr>
				
				<div class="row order">
					<div class="row left">
					상품명 : ㅇㅇㅇㅇㅇㅇ <br><br>
					가격 : ㅇㅇㅇㅇㅇㅇ
					<br><br>
					<button>삭제</button> <button>담기</button>					
					</div>				
				</div>				
				<hr>			
							
	
				
	
			</div>
			</div>
</div>

	

    
    <jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>