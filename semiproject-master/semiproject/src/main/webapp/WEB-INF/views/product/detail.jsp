<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="${productDto.productName}" name="title"/>
</jsp:include>

<!-- 별 스코어 링크 -->
<script src="https://cdn.jsdelivr.net/gh/hiphop5782/score@latest/score.js"></script>

<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_detail.css">

<script type="text/javascript">

	$(function(){
	    $(".item-score").score({
	    	starColor:"red", // 별 색상 (기본 : 금색)
            integerOnly:false, // 반올림
            display:{
                showNumber:false, // 숫자 표시
                placeLimit:1, // 소수점 자릿수
                textColor:"red", // 숫자 색상 (기본 : 금색)
            }
	    });
	});
	
	$(function(){
	    $(".review-score").score({
	    	starColor:"red", // 별 색상 (기본 : 금색)
            integerOnly:false, // 반올림
            display:{
                showNumber:false, // 숫자 표시
                placeLimit:1, // 소수점 자릿수
                textColor:"red", // 숫자 색상 (기본 : 금색)
            }
	    });
	    
	    <!-- 수량 버튼 이벤트 -->
	    //마이너스버튼
	    $(".btndown").click(function(){
	    	var inputCount = parseInt($(this).next().val()); //input태그 값선택
	    	$(".itemnumbercheck").val(inputCount-1); //input태그에 값 넣기
	    	if(inputCount==2){
	    		$(this).attr("disabled",true); //마이너스 버튼 비활성화 설정
	    	}
	    });
	    
	    //플러스버튼
	    $(".btnup").click(function(){
	    	$(".btndown").attr("disabled",false); //마이너스 버튼 비활성화 해제
	    	var inputCount = parseInt($(this).prev().val()); //input태그 값
	    	$(".itemnumbercheck").val(inputCount+1);
	    });
	    
	    //직접 입력
/* 	    $(".itemnumbercheck").blur(function(){
	    	var inputCount = parseInt($(this).prev().val());
	    	if(inputCount<1){
	    		alert('수량은 1개 이상 선택해주세요!');
	    	}else{
	    		$(".itemnumbercheck").val(1);
	    	}
	    }); */
	    
	});
	
	
	$(function(){
	    
	    <%-- 누르는 버튼에 따라 전송하는 Mapping이 다르도록 설정 --%>
		// 만약 구매 버튼을 눌렀다면 
 	    $("#submit-purchase").click(function(){
	        $("#product-data").attr("action", "${pageContext.request.contextPath}/basket/insert"); // 상품 구매 Mapping으로 전송
	        $("#product-data").attr("method", "get"); // get 방식
	    }); 
		
		// 만약 장바구니 버튼을 눌렀다면 /basket Mapping으로 form의 값을 전송
	    $("#submit-basket").click(function(){
	        $("#product-data").attr("action", "detail"); // 장바구니 등록 Mapping으로 전송
	        $("#product-data").attr("method", "post"); // post 방식
	    });
	});
	
</script>

<style>

	#product-good {
		color : red;
	}
	
	#review-good {
		color : red;
	}

	td{
    	vertical-align : middle;
    	line-height : 1.2em;
    	font-size : 14px;
    }
    
    .table.table-border > thead > tr > th,
	.table.table-border > thead > tr > td,
	.table.table-border > tbody > tr > th,
	.table.table-border > tbody > tr > td,
	.table.table-border > tfoot > tr > th,
	.table.table-border > tfoot > tr > td {  
	    border: 1px solid #e5e7eb;
	}
	
	#td-top{
		text-align: left;
		vertical-align : top;
		margin-top: 20px;
	}
	
	#td-font1{
		 font-size: 20px;
		 color : #848484;
	}
	
	#td-font2{
		 font-size: 20px;
		 color : #58FA82;
	}
	
	#td-font3{
		 font-size: 20px;
		 font-weight: bold;
	}    
	
	#td-font4{
		 font-size: 20px;
	}
    
</style>

<!--본문 시작-->
<form id = "product-data"> <%-- form 시작 --%>
<section class="itemsection1">
	<div class="inner">
	<input type = "hidden" name = "productNo" value = "${productDto.productNo}"> <%-- 상품 번호 --%>
	<input type = "hidden" name = "productName" value = "${productDto.productName}"> <%-- 상품 이름 --%>
		<c:forEach var="productTumbnailList" items="${productTumbnailList}">
			<img class="itemmain" src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${productTumbnailList.attachmentNo}" alt="상품메인">
		</c:forEach>
		<div class="itemtitle">
    		<p>${productDto.productName}<p>
		<div class="itemstar">
    		<div id = "product-good" class="item-score" data-max="5" data-rate="${productDto.productGood}"></div>
    		<span id = "product-good">${productDto.productGood}</span>
		</div> 
		<div class="itemtitle1">
    		<span>${productDto.productPrice}</span>
			<span>원</span>
			<ul>
			    <li>배송정보</li>
			    <li>3000원 / 주문시결제(선결제)</li>
			    <li>배송방법(새벽/일반) 및 [수령일 지정]은 주문서/결제 단계에서 선택 가능합니다.</li>
			</ul>
			
			<dl class="itemcount">
	    		<dt id="itemcountall">총 수량</dt>
	    		<dd>
	        		<div class="countcheck">
		            	<span class="numbericon">
		                	<button type="button" class="btndown" title="감소" value="dn^|^0" disabled>감소</button>
		                		<input type = "text" class="itemnumbercheck" name = "productCount" value="1" readonly>
		                	<button type="button" class="btnup" title="증가" value="up^|^0">증가</button>
		            	</span> <!--//numbericon-->
					</div> <!--//countcheck-->
	    		</dd> 
			</dl> <!--//itemcount-->
			<div class="itembox">
	    		<div class="inner">
	        		<%-- 누르는 버튼에 따라 전송하는 Mapping이 다르도록 하기 위해 하나의 폼에 버튼 2개 생성 --%>
					<button type = "submit" id = "submit-purchase">
						<img src="${pageContext.request.contextPath}/image/buyitem.png" alt="바로구매">
					</button>
	
					<button type = "submit" id = "submit-basket">
						<img src="${pageContext.request.contextPath}/image/addcart.png" alt="장바구니">
					</button>
	            </div> <!--//inner-->
			</div> <!--//itembox-->
				
				</div> <!-- //itemtitle1 -->
			</div> <!-- //itemtitle -->
	</div> <!--//inner-->
</section> <!--//itemsection1-->
</form> <%-- form 끝 --%>
		 
<section class="itemsection2">
	<div class="inner">
  		<div class="itemimage">
  			<c:forEach var="productDetailList" items="${productDetailList}">
				<img src="${pageContext.request.contextPath}/attachment/download/productDetail?attachmentNo=${productDetailList.attachmentNo}" alt="상품설명">
			</c:forEach>
		</div> <!--//itemimage-->
	</div> <!--//inner-->
</section> <!--//itemsection2-->

<%-- 리뷰 관련 --%>
<div class = "container-800 mt-40" style="margin-bottom:100px">
	<c:forEach var = "productReviewList" items = "${productReviewList}">
		<table class="table mt-10 mb-10">
    		<tr>
				<td class="row left" width="400" height="30">
   					<div class="row" style="font-size: 16px; font-weight: bold;">
						제목 : ${productReviewList.reviewTitle}
					</div>
					옵션 : ${productReviewList.paymentOption} <br>
					내용 : ${productReviewList.reviewContent} <br>
				</td>
       			<td class="img center" width="100">
       				<c:choose>
       					<c:when test = "${productReviewList.reviewAttachmentNo != ''}">
       						<img width=90 height=90 src="${pageContext.request.contextPath}/attachment/download/reviewImg?attachmentNo=${productReviewList.reviewAttachmentNo}">
       					</c:when>
       					<c:otherwise>
       						<div style = "background-color : white; width : 90px; height : 90px;"></div>
       					</c:otherwise>
       				</c:choose>
				</td>
				<td class="row center" width="30">
   					${productReviewList.reviewWritetime} <br>
					${productReviewList.reviewId} <br>
					<div class="row">
  						<div id = "review-good" class="review-score" data-max="5" data-rate="${productReviewList.reviewGood}"></div>
  						<span id = "review-good" >${productReviewList.reviewGood}</span>
         			</div>
       			</td>
    		</tr>
		</table>
		<hr>
		<br>
	</c:forEach>
</div>

<%-- 사용할 수 있는 값들
${productDto.productNo}
${productDto.categoryHighNo}
${productDto.categoryLowNo}
${productDto.productName}
${productDto.productPrice}
${productDto.productInformation}
${productDto.productInventory}
${productDto.productGood}
${productDto.productRegisttime}
${productDto.productUpdatetime
${productDto.productInactive}

<c:forEach var="productTumbnailList" items="${productTumbnailList}">
	<img width=50 height=50 src="/attachment/download/productTumbnail?attachmentNo=${productTumbnailList.attachmentNo}">
</c:forEach>

<c:forEach var="productDetailList" items="${productDetailList}">
	<img width=50 height=50 src="/attachment/download/productDetail?attachmentNo=${productDetailList.attachmentNo}">
</c:forEach>

<c:forEach var = "productReviewList" items = "${productReviewList}">
	${productReviewList.reviewId}
	${productReviewList.reviewWritetime}
	${productReviewList.paymentOption}
	${productReviewList.reviewGood}
	${productReviewList.reviewTitle}
	${productReviewList.reviewContent}
	<img width=50 height=50 src="/attachment/download/reviewImg?attachmentNo=${productReviewList.reviewAttachmentNo}">
</c:forEach>
--%>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>