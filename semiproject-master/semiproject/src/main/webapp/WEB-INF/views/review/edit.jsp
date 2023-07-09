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
    $(".star-score-edit").score({
        editable:true,//수정 가능하도록 설정
        integerOnly:true,//정수만 가능
        display:{
            showNumber:true,//숫자표시
        },
        send:{
            sendable:true,
            name:"reviewGood"
        }
    });
});
</script>

<div class = "container-900 mt-40 mb-40 center">
	<div class="row left">
		<h1>리뷰 수정</h1>
	</div>
	<hr>
</div>
<div>
	<div class="container-800">
		<form action = "edit" method = "post" enctype = "multipart/form-data">
			<%-- 인터셉터 통과를 위해 주문번호 포함 --%>
			<input name = "orderNo" value = "${reviewPaymentNoVO.orderNo}" type = "hidden">
			<input name = "reviewNo" value = "${reviewVO.reviewNo}" type = "hidden">
			<input name = "reviewGoodBefore" value = "${reviewVO.reviewGood}" type = "hidden">
			
			<input name = "reviewPaymentNo" value = "${reviewVO.reviewPaymentNo}" type = "hidden">
			<input name = "paymentProductNo" value = "${reviewVO.paymentProductNo}" type = "hidden">
			
			<div class = "row">	
				<input class="input input-underline w-50" name = "reviewTitle" type = "text" value="${reviewVO.reviewTitle}">
			</div>
			<div class = "row mt-20">
				<label class="float-right">
					<%-- 리뷰 별점 --%>
					<div class = "star-score-edit" name = "reviewGood" data-max="5" data-rate = "${reviewVO.reviewGood}"></div>
					<%-- <input class="input input-underline w-50" name = "reviewGood" type = "number" value="${reviewVO.reviewGood}"> --%>
				</label>
				<textarea name = "reviewContent" class="input w-100 fix-size mt-10" rows="8" >${reviewVO.reviewContent}</textarea>
			</div>
			<div class = "row right">
				<a href="${pageContext.request.contextPath}/mypage/order_list" class="btn btn-neutral">취소</a>
				<button type = "submit" class="btn">작성</button>
			</div>
		</form>
	</div>
</div>


<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
