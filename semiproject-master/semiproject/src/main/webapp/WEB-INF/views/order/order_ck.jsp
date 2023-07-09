<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="주문서 작성" name="title"/>
</jsp:include>

<style>

	.sameinfo{
        font-size:12px;
        float:right;
	}
    textarea.memo{
      font-size:15px;
     }
     .error-message{
     	display:none;
     	color:red;
     }
     .point.error ~ .error-message{
     	display:block;
     }
     .green{
     	color:#4CB756;
     	font-weight: bold;
     	font-size:23px;
     }
     .icon-left{
     	float:left;
     	color:#4CB756;
     	font-size:20px;
     }
     .icon-equal{
     	float:left;
     	color:black;
     	font-size:20px;
     }

</style>

<script type="text/javascript">
	$(function(){
		<!-- 수령자 정보와 주문자 정보가 같은 경우의 체크박스 -->
		$("#checkbox").on("input", function(){
			var judge = $(this).prop("checked");
			if(judge){
				var orderName = $(".infoName").text();
				$("input[name=orderName]").val(orderName);
				var orderTel = $(".infoTel").text();
				$("input[name=orderTel]").val(orderTel);
			}else{
				$("input[name=orderName]").val("");
				$("input[name=orderTel]").val("");
			}
		});
		
		<!-- 최종 금액 산출 -->
    	<!-- 상품 금액(콜백함수) -->
    	function productPrice(){
    		var price;
    		var cnt;
    		var sum = 0;
    		var judge = $(".items-count").length==1;
    		// 바로구매 또는 장바구니 상품 1개만 넘어올 경우
    		if(judge){
    			price = parseInt($(".items-price").text());
    			cnt = parseInt($(".items-count").text());
    			sum = price*cnt;
    		// 장바구니에서 여러 상품이 넘어올 경우
    		}else{
    			$(".items-count").each(function(){
    				price = parseInt($(this).parent().next().children().text());
    				cnt = parseInt($(this).text());
    				sum += (price*cnt);
    			});
    		}
    		return sum;
		}
		
		
		<!-- 배송비(콜백함수) -->
		function deliveryFee(){
			var itemsPrice = parseInt($(".before-price").text()); //상품금액
			var delivery;
			if(itemsPrice>=50000){
				delivery=0;
			}else{
				delivery=3000;
			}
			return delivery;
		}
		
		<!-- 총 금액(콜백함수) -->
		function totalPrice(totalItemPrice, totalDelivery, totalDiscount){
			var totalItemPrice = parseInt($(".before-price").text());
			var totalDelivery = parseInt($(".delivery-price").text());
			var totalDiscount = parseInt($(".point-price").text());
			var totalCoupon = parseInt($(".coupon-price").text());
			//총금액(상품금액+배송비-적립금-쿠폰)
			var totalPrice = (totalItemPrice+totalDelivery)-totalDiscount-totalCoupon;
      
			return totalPrice;
		}
 		
		
		<!-- 적립금 전체 사용 버튼 이벤트 -->
		$(".point-btn").click(function(e){
			e.preventDefault(); //a 태그 전송 막기
			var point = ${memberDto.memberPoint}; //보유 적립금
			var itemsPrice = parseInt($(".before-price").text()); //상품 가격
			var delivery = deliveryFee(); //배송비
			var coupon = parseInt($(".coupon-price").text()); //쿠폰 할인금액
			var total = itemsPrice+deliveryFee()-coupon; //상품가격+배송비-쿠폰
			
			// if문(true) : 총 상품금액(상품가격+배송비) < 적립금, 금액만큼 사용하도록 최대값 설정
			if(total<point){
				$("input[name=orderUsePoint]").val(total); //클릭하면 입력창에 전체적립금 표시
				$(".point-price").text(total); //할인창에 할인금액표시
				$(".after-price").text(totalPrice(itemsPrice,delivery,total,coupon));
				$("[name=orderType]").attr("disabled",true); //결제수단 라디오버튼 비활성화
			// if문(false) : 총 상품금액(상품가격+배송비-쿠폰) > 적립금	
			}else{
				$("input[name=orderUsePoint]").val(point);
				$(".point-price").text(point);
				var discount = $(".point-price").text();
				$(".after-price").text(totalPrice(itemsPrice,delivery,discount,coupon));
				$("[name=orderType]").attr("disabled",false);
			}
			var inputValue = parseInt($(".after-price").text());
			$('input[name=orderPayPrice]').val(inputValue); //총 결제금액
		});
		
		<!-- 적립금 일부 사용 블러 이벤트 -->
		$("input[name=orderUsePoint]").blur(function(){
			$("[name=orderType]").attr("disabled",false);
			var itemsPrice = parseInt($(".before-price").text()); //상품 가격
			var delivery = deliveryFee(); //배송비
			var total = itemsPrice+deliveryFee(); //상품가격+배송비	
			var point = ${memberDto.memberPoint}; //보유 적립금
			var inputPoint = $(this).val();	//입력값
			
			$(this).removeClass("error");
			if(inputPoint>point){// 보유포인트보다 입력값이 크면 에러문구 표시
				$(this).addClass("error");
				$(this).text(0);
				$(".point-price").text(0);
				$(".after-price").text(totalPrice(itemsPrice,delivery,0));
			}else if(inputPoint==""){ //입력값이 없으면 할인 0 으로 출력
				$(".point-price").text(0);
				$(".after-price").text(totalPrice(itemsPrice,delivery,0));
			}else{ //상품가격보다 적게 입력하면 입력값으로 할인금액 표시
				$(".point-price").text(inputPoint); //할인금액 출력
				var discount = $(".point-price").text();
				$(".after-price").text(totalPrice(itemsPrice,delivery,discount));
			}
			var inputValue = parseInt($(".after-price").text());
			$('input[name=orderPayPrice]').val(inputValue); //총 결제금액
		});

		
		<!-- 하단 금액 부분 (고정값)출력 -->
		$(".before-price").text(productPrice()); //상품금액
		$(".delivery-price").text(deliveryFee()); //배송비
		var totalItemPrice = $(".before-price").text();
		$('input[name=orderPrice]').val(totalItemPrice); //할인전 금액(총상품가격) value값 넣기
		var totalDelivery = $(".delivery-price").text();
		var totalDiscount = $(".point-price").text();
		$(".after-price").text(totalPrice(totalItemPrice, totalDelivery)); //총 금액
		var inputValue = parseInt($(".after-price").text());
		$('input[name=orderPayPrice]').val(inputValue); //총 결제금액 value값 넣기
		
		<!-- 적립예상금액 계산 후 value값 넣기 -->
		var result = ($(".before-price").text())*0.01;
		$('input[name=orderPoint]+span').text(result); //화면에 표시
		$('input[name=orderPoint]').val(result); //주문테이블 등록을 위한 hidden값 value처리
		
		<!--쿠폰 -->
		$(".coupon-btn").on("click", function(){
			var condition = parseInt(30000); //쿠폰 사용 기준금액(3만원 이상)
			var judge = productPrice()+deliveryFee(); //상품금액+배송비
			
			var itemsPrice = parseInt($(".before-price").text()); //상품 가격
			var point = $(".point-price").text(); //포인트 할인가격
			var total = itemsPrice+deliveryFee()-point; //상품가격+배송비-적립금
			var coupon = $(".coupon-price").text();
			
			if(total==0){
				alert('총 결제금액이 0원입니다!');
			}else if(total<4000){
				alert('총 결제금액보다 쿠폰 할인금액이 더 큽니다!');
			}else{
				if(judge>=condition){
					$("[name=inputCoupon]").val(4000);
					$(".coupon-price").text(4000);
					$(".after-price").text(totalPrice(itemsPrice, deliveryFee(), point, coupon));
				}else{
					alert('주문금액이 3만원 이상일 경우 쿠폰적용이 가능합니다!');
				}
			}

		});
		
		<!-- 결제금액 0일 경우 라디오버튼 비활성화 -->
		var zero = $(".after-price").text();
		if(zero==0){
			$("[name=orderType]").attr("disabled",true);
		}else{
			$("[name=orderType]").attr("disabled",false);
		}

	});

	
    $(function(){
		$("#select-categoryHigh").on("change", function(){
			// 상위 카테고리 선택창의 값(value)을 변수로 설정
			var categoryHighNo = $("#select-categoryHigh").val();
			// 하위 카테고리에 해당하는 select 태그는 유지하며 내부의 선택 항목 삭제(초기화)
			$("#select-categoryLow").empty();
			$.ajax({
				// 상위 카테고리 번호를 매개변수로 전체 조회 수행
				url:"${pageContext.request.contextPath}/rest/product/categoryLow?categoryHighNo="+categoryHighNo,
                method:"get",
                success:function(resp){
					// 상위 카테고리에 연결된 하위 카테고리의 수만큼 option 태그 생성
					for(var i = 0 ; i < resp.length ; i ++) {
						// 하위 카테고리 option 태그 생성 template을 변수로 설정
						var option = $("#option-categoryLow").text();
						// 임시로 작성했던 하위 카테고리 번호(categoryLowNo)를 resp의 categoryLowNo으로 변환
						option = option.replace("{{categoryLowNo}}", resp[i].categoryLowNo);
						// 임시로 작성했던 하위 카테고리 이름(categoryLowName)를 resp의 categoryLowName으로 변환
						option = option.replace("{{categoryLowName}}", resp[i].categoryLowName);
						// 변환이 끝난 option을 변수 result로 지정
						var result = $(option);
						// result를 이용하여 option 태그 생성
						$("select[name=categoryLowNo]").append(result);
					}
                }
			});
		});
	});
</script>



	<div class="container-900 mt-50">
        <div class="row">
            <h2>주문 / 결제</h2>
        </div>

        <hr>
<form action="order_ck" method="post">	
        <div class="row mt-50">
          <h3>주문 상품 정보</h3>
        </div>

        <table class="table table-border">
          <thead>
            <tr>
              <th class="w-70">상품</th>
              <th class="w-15">수량</th>
              <th class="w-15">가격</th>
            </tr>
          </thead>
          <tbody>
          <c:choose>
	          <c:when test="${basketList.size()>0}">
		            <c:forEach var="list" items="${basketList}">
		            	<tr>
			              	<td>
			              		<div>${list.productName}</div>
			              		<div class="mt-10">${list.basketProductOption}</div>
			              	</td>
			              	<td>
			              		<div class="center items-count">${list.basketCountNumber}</div>
			              	</td>
			              	<td>
			              		<div class="center items-price">${list.productPrice}</div>
			              	</td>
		            	</tr>
		            </c:forEach>
	            	<!-- payment에 넘어가야하는 정보(paymentDto형태로 들어가야함!) -->
			        <c:forEach var="list" items="${basketList}" varStatus="status">
			        	<input type="hidden" name="payment[${status.index}].basketNo" value="${list.basketNo}">
						<input type="hidden" name="payment[${status.index}].paymentProductNo" value="${list.basketProductNo}">
						<input type="hidden" name="payment[${status.index}].paymentCount" value="${list.basketCountNumber}">
						<input type="hidden" name="payment[${status.index}].paymentPrice" value="${list.productPrice}">
						<input type="hidden" name="payment[${status.index}].paymentOption" value="${list.basketProductOption}">
					</c:forEach>
	            </c:when>
	            <c:otherwise>
	            	<tr>
		              <td>
		              	<div>${purchaseList.productName}</div>
		              	<div class="mt-10">${purchaseList.basketProductOption}</div>
		              </td>
		              <td>
		              	<div class="center items-count">${purchaseList.basketCountNumber}</div>
		              </td>
		              <td>
		              	<div class="center items-price">${purchaseList.productPrice}</div>
		              </td>
	            	</tr>
	            	<!-- payment에 넘어가야하는 정보(paymentDto형태로 들어가야함!) -->
			        <input type="hidden" name="basketNo" value="${purchaseList.basketNo}">
					<input type="hidden" name="paymentProductNo" value="${purchaseList.basketProductNo}">
					<input type="hidden" name="paymentCount" value="${purchaseList.basketCountNumber}">
					<input type="hidden" name="paymentPrice" value="${purchaseList.productPrice}">
					<input type="hidden" name="paymentOption" value="${purchaseList.basketProductOption}">
	            </c:otherwise>
            </c:choose>
            <tr>
              <td colspan="3">
                <i class="fa-solid fa-angles-right"></i>
                적립예정 <input type="hidden" name="orderPoint" value="">
                <span></span>원
              </td>
            </tr>
          </tbody>
        </table>
        
	
        <div class="row mt-50">
          <h3>주문자 정보</h3>
        </div>

        <table class="table table-border">
          <tbody class="left">
            <tr>
              <th>이름</th>
              <td>
                <span class="infoName">${memberDto.memberName}</span>
              </td>
            </tr>
            <tr>
              <th>이메일</th>
              <td>
                <span>${memberDto.memberEmail}</span>
              </td>
            </tr>
            <tr>
              <th>연락처</th>
              <td>
                <span class="infoTel">${memberDto.memberTel}</span>
              </td>
            </tr>
          </tbody>
        </table>

        <div class="row mt-50">
          <h3>배송 정보
            <label class="sameinfo">
              <input type="checkbox" id="checkbox">
              <span>위 정보와 같음</span>
            </label>
          </h3>
        </div>

        <div class="row">
          <table class="table table-border">
            <tbody class="left">
              <tr>
                <th>이름</th>
                <td>
                  <input type="text" name="orderName" placeholder="수령인" required>
                </td>
              </tr>
              <tr>
                <th>연락처</th>
                <td>
                  <input type="text" name="orderTel" placeholder="연락처" required>
                </td>
              </tr>
              <tr>
                <th>주소</th>
                <td>
                  <input type="text" name="orderPost" placeholder="우편번호" required>
                  <input type="text" name="orderBaseAddress" placeholder="주소" required>
                  <input type="text" name="orderDetailAddress" placeholder="상세주소" required>
                </td>
              </tr>
              <tr>
                <th class="va-middle">배송메세지</th>
                <td>
                  <textarea name="orderMemo" class="memo w-100 input" placeholder="ex) 배송전에 연락주세요!"></textarea>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <div class="row mt-50 mb-10">
          <h3>쿠폰 / 적립금</h3>
        </div>

        <div class="row">
          <div class="row">
            <p>쿠폰 (보유 : <span>${couponUsable}</span>개)                
				<select name="couponIssue" id = "selectBox">
                	<option value="">선택안함</option>	
                	<c:forEach var="couponUse" items="${couponUse}">
                		<option value="${couponUse.couponIssue}">${couponUse.couponName}</option>
                	</c:forEach>
				</select>     		            			              				  
        	</p> 
        </div>
        <div>  
            <input type="text" class="input w-50" name="inputCoupon" disabled>
            <a href="#" class="btns btns-neutral coupon-btn">쿠폰 적용</a>
        </div>
        <div class="row mt-20">
          	<p>적립금 (사용가능 적립금 : <span>${memberDto.memberPoint}</span>원)</p>
			<input type="text" class="input w-50 point mt-10" name="orderUsePoint" value="0">
			<span class="error-message">금액이 부족합니다!</span>
			<a href="#" class="btns btns-neutral point-btn">전액 사용</a>
        </div>
    </div>


        <div class="row mt-50">
          <h3>주문상품 할인적용</h3>
        </div>

        <div class="row center">
          <table class="table table-slitt">
            <tbody>
              <tr>
                <th>상품금액</th>
                <th>
                	<span class="icon-left">
                		<i class="fa-solid fa-circle-plus"></i>
                	</span>배송비
                </th>
                <th>
                	<span class="icon-left">
                		<i class="fa-solid fa-circle-minus"></i>
                	</span>적립금
                </th>
                <th>
                	<span class="icon-left">
                		<i class="fa-solid fa-circle-minus"></i>
                	</span>쿠폰
                </th>
                <th>
                	<span class="icon-equal">
                		<i class="fa-solid fa-equals"></i>
                	</span>총 금액
                </th>
              </tr>
              <tr>
                <td>
                	<div>
                		<span class="before-price">0</span>원
                	</div>
                </td>
                <td>
                	<div>
                		<span class="delivery-price">0</span>원
                	</div>
                </td>
                <td>
                	<div>
                		<span class="point-price">0</span>원
                	</div>
                </td>
                <td>
                	<div>
                		<span class="coupon-price">0</span>원
                	</div>
                </td>
                <td>
                	<div class="green">
                		<span class="after-price">0</span>원
                	</div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="row mt-50">
          <h3>결제 수단</h3>
        </div>

        <div class="row">
            <input type="radio" name="orderType" value="신용카드">신용카드
            <input type="radio" name="orderType" value="실시간 계좌이체">실시간 계좌이체
            <input type="radio" name="orderType" value="무통장입금">무통장입금
        </div>
        
         <!-- orders에 넘어가야하는 정보 -->
        <input type="hidden" name="orderStatus" value="결제완료">
        <input type="hidden" name="orderPayPrice" value="">
        <input type="hidden" name="orderPrice" value="">
        
        <div class="row center">
          <button type="submit" class="btns btns-positive purchase-btn">결제하기</button>
        </div>
	</form>

    </div>
</body>

</html>
    
    <jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>