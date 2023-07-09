<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="리뷰 작성" name="title"/>
</jsp:include>

<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hiphop5782/score@latest/score.min.js"></script>

<script type="text/javascript">
	
	$(function(){
	    $(".review-score-edit").score({
	    	starColor:"red", // 별 색상 (기본 : 금색)
	    	editable:true,  // 수정 가능
            integerOnly:true,   // 반올림
            
            display:{ 
                showNumber:true, // 숫자 표시
                textColor:"red", // 숫자 색상 (기본 : 금색)
            },

            send:{
                sendable:true, // 전송 가능 여부
                name:"reviewGood", // 전송 파라미터명\
            }
	    });
	});
	
</script>

<div class = "container-900 mt-40 mb-40 center">
	<div class="row left">
		<h1>리뷰 작성</h1>
	</div>
	
	<hr>
	
		<div class = "container-800">
		<br>
			<div class = "float-left mt-10">
				<img src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${productDto.productAttachmentNo}" width=100 height=100>
			</div>
			
			<div class = "container-550">
			<br>
				<div class = "row left">
					상품 이름 : ${productDto.productName}
				</div>
				<div class = "row left">
					상품 가격 : ${productDto.productPrice}
				</div>
				<div class = "row left">
					상품 품절여부 : ${productDto.productInactive}
				</div>
			</div>
		</div>
</div>
<div>
	<div class="container-800">
		<form action = "write" method = "post" enctype = "multipart/form-data">
			<%-- 인터셉터 통과를 위해 주문번호 포함 --%>
			<input name = "orderNo" value = "${reviewPaymentNoVO.orderNo}" type = "hidden">
			<%-- 리뷰 작성을 누를 때 등록 전 별점의 총점과 리뷰의 갯수를 구하기 위함 --%>
			<input name = "paymentProductNo" value = "${reviewPaymentNoVO.paymentProductNo}" type = "hidden">
			<%-- 리뷰 등록에 필요한 항목 --%>
			<input name = "paymentNo" value = "${reviewPaymentNoVO.paymentNo}" type = "hidden">
			
			<div class = "row">	
				<%-- 리뷰 제목 --%>
				<input class="input input-underline w-50" name = "reviewTitle" type = "text" placeholder="제목">
			</div>
			<div class = "row mt-20">
				<label class="float-right">
					<%-- 리뷰 별점 --%>
			<%-- 		<div class = "star-score-edit" data-max="5" data-rate = "${productList.productGood}"></div> --%>
					<div class="review-score-edit" data-max="5"></div>
					<!-- <input class="input input-underline w-50" name = "reviewGood" type = "number" placeholder="별점"> -->
				</label>
				<textarea name = "reviewContent" class="input w-100 fix-size mt-10" rows="8" placeholder="내용"></textarea>
			</div>
				<i class="fa-solid fa-camera"></i>
				<input class="row" name = "attachmentReviewImg" type="file" accept = ".png, .jpg" >
			<div class = "row right">
				<a href="${pageContext.request.contextPath}/mypage/order_list" class="btn btn-neutral">취소</a>
				<button type = "submit" class="btn">작성</button>
			</div>
		</form>
	</div>
</div>


<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
